package com.pk.ysf.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YourSoccerFieldApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<YourSoccerFieldApplication>(*args)
        }
    }
}
