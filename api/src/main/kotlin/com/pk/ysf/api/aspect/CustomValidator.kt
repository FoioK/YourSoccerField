package com.pk.ysf.api.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.IllegalArgumentException
import javax.validation.ConstraintViolation
import javax.validation.Validator

@Aspect
@Component
class CustomValidator @Autowired constructor(
        private val validator: Validator
) {

    @Before(value = "@annotation(com.pk.ysf.api.annotation.ValidInput)")
    @Throws(Throwable::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validate(joinPoint: JoinPoint) {
        val result: List<String> = validateProcess(joinPoint.args.toList())
        if (result.isEmpty()) throw IllegalArgumentException(result.toString()) else return
    }

    private fun validateProcess(params: List<Any>): List<String> {
        val message: List<String> = emptyList()
        repeat(params.size) {
            val constraints = validator.validate(params[it])
            if (!constraints.isEmpty()) {
                message.plus(formatMessage(constraints))
            }
        }

        return message
    }

    private fun formatMessage(input: MutableSet<ConstraintViolation<Any>>): List<String> {
        val response: List<String> = emptyList()
        input.forEach {
            response.plus("${it.propertyPath} ${it.message}")
        }

        return response
    }
}
