package com.ohayo.moyamoya.global.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import jakarta.annotation.PostConstruct
import mu.KLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FirebaseConfig(
    private val logger: KLogger,
) {
    @PostConstruct
    fun init() {
        try {
            val stream = ClassPathResource("secret/moyamoya-firebase.json").inputStream
            val opt = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(opt)
                logger.info("Firebase is initialized.")
            } else {
                logger.info("Firebase is already initialized!")
            }
        } catch (e: Exception) {
            logger.error("Error while initializing firebase: ", e)
        }
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
}