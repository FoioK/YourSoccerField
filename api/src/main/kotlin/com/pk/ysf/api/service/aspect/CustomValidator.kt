package com.pk.ysf.api.service.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.validation.Validator

@Aspect
@Component
class CustomValidator @Autowired constructor(
        private val validator: Validator
) {

    @Before(value = "@annotation(com.pk.ysf.api.annotation.ValidInput)")
    @Throws(Throwable::class)
    fun exampleValid(joinPoint: JoinPoint) {
        validate(joinPoint.args.toList())
    }

    private fun validate(params: List<Any>) {
        repeat(params.size) {
            val constraint = validator.validate(params[it])
            if (!constraint.isEmpty()) {

            }
        }
    }
}
