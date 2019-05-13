package com.pk.ysf.api.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value("\${project.name}")
    private val name: String? = null

    @Value("\${project.version}")
    private val version: String? = null

    @Value("\${project.description}")
    private val description: String? = null

    private val apiInfo: ApiInfo
        get() = ApiInfo(
                name,
                description,
                version,
                "",
                Contact("", "", ""),
                "MIT License",
                "https://opensource.org/licenses/MIT",
                emptyList()
        )

    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pk.ysf"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.apiInfo)
    }
}
