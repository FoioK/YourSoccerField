package com.pk.ysf.api.feign

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients
@ImportAutoConfiguration(
        RibbonAutoConfiguration::class,
        FeignRibbonClientAutoConfiguration::class,
        FeignAutoConfiguration::class
)
class CloudConfiguration
