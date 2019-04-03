package com.pk.ysf.api.security

import com.pk.ysf.apimodels.entity.CustomUserDetail
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = ["com.pk.ysf"])
@EntityScan("com.pk.ysf.apimodels.*")
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(currentUserHandlerMethodArgumentResolver())
    }

    @Bean
    public fun currentUserHandlerMethodArgumentResolver(): HandlerMethodArgumentResolver =
            object : HandlerMethodArgumentResolver {

                override fun supportsParameter(parameters: MethodParameter): Boolean =
                        parameters.parameterType == CustomUserDetail::class.java

                override fun resolveArgument(
                        parameter: MethodParameter,
                        mvcContainer: ModelAndViewContainer?,
                        webRequest: NativeWebRequest,
                        binderFactory: WebDataBinderFactory?
                ): Any? =
                        try {
                            SecurityContextHolder.getContext()
                                    .authentication
                                    .principal
                        } catch (e: Exception) {
                            null
                        }
            }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}