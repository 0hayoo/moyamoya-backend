package com.ohayo.moyamoya

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
class MoyamoyaApplication

fun main(args: Array<String>) {
    runApplication<MoyamoyaApplication>(*args)
}

@Component
class EnvironmentPrinter(
    private val environment: Environment
): CommandLineRunner {
    override fun run(vararg args: String) {
        environment.activeProfiles.forEach { 
            println(it)
        }
    }
}