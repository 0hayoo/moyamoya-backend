package com.ohayo.moyamoya

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MoyamoyaApplication

fun main(args: Array<String>) {
    runApplication<MoyamoyaApplication>(*args)
}
