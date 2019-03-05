package com.pk.ysf.api.aspect

import com.pk.ysf.apimodels.exception.InvalidInputException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.validation.ConstraintViolation
import javax.validation.Validator
import kotlin.streams.toList

@Aspect
@Component
class CustomValidator @Autowired constructor(
        private val validator: Validator
) {

    @Before(value = "@annotation(com.pk.ysf.api.annotation.ValidInput)")
    @Throws(Throwable::class)
    fun validate(joinPoint: JoinPoint) {
        val result: List<String> = validateProcess(joinPoint.args.toList())
        if (result.isNotEmpty()) throw InvalidInputException(
                result.joinToString { it }
        ) else return
    }

    private fun validateProcess(params: List<Any>): List<String> {
        return params.stream()
                .map { validator.validate(it) }
                .filter { it.isNotEmpty() }
                .map { formatMessage(it) }
                .toList()
    }

    private fun formatMessage(input: MutableSet<ConstraintViolation<Any>>): String =
            input
                    .map { "${it.propertyPath} ${it.message}" }
                    .toTypedArray()
                    .joinToString(
                            separator = ";"
                    ) { it }

}
