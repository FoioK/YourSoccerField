package com.pk.YourSoccerField.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @Value("${project.description}")
    private String description;

    @Value("${server.address}")
    private String address;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pk.YourSoccerField"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                name,
                description,
                version,
                "",
                new Contact("", "", ""),
                "MIT License",
                "https://opensource.org/licenses/MIT",
                Collections.emptyList()
        );
    }
}
