package com.ohayo.moyamoya.infra.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("cloud.aws")
class S3Properties @ConstructorBinding constructor(
    val credentials: Credentials,
    val s3bucket: String,
    val region: Region,
) {
    class Credentials(
        val accessKey: String,
        val secretKey: String
    )
    class Region(
        val static: String
    )
}