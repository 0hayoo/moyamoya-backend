package com.ohayo.moyamoya.global.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.ohayo.moyamoya.infra.s3.S3Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    private val s3Properties: S3Properties
) {
    @Bean
    fun AmazonS3Client(): AmazonS3Client = AmazonS3ClientBuilder.standard()
        .withRegion(s3Properties.region.static)
        .withCredentials(
            AWSStaticCredentialsProvider(
                BasicAWSCredentials(
                    s3Properties.credentials.accessKey,
                    s3Properties.credentials.secretKey
                )
            )
        )
        .build() as AmazonS3Client
}