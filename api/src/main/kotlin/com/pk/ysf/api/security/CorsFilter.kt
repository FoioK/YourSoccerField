package com.pk.ysf.api.security

import org.apache.camel.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST)
class CorsFilter : Filter {

    override fun init(filterConfig: FilterConfig) {

    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val response: HttpServletResponse = servletResponse as HttpServletResponse
        val request: HttpServletRequest = servletRequest as HttpServletRequest

        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
        response.setHeader("Access-Control-Max-Age", "")
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type")

        if ("OPTIONS".equals(request.method)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain.doFilter(request, response)
        }
    }

    override fun destroy() {

    }
}