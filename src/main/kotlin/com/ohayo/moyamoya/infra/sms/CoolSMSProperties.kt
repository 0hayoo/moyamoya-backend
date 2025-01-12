package com.ohayo.moyamoya.infra.sms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("coolsms")
class CoolSMSProperties @ConstructorBinding constructor(
    val apiKey: String,
    val apiSecret: String,
    val senderPhone: String
)