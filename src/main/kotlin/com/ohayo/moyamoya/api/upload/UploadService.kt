package com.ohayo.moyamoya.api.upload

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.ohayo.moyamoya.global.CustomException
import com.ohayo.moyamoya.infra.s3.S3Properties
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class UploadService(
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Properties
) {
    fun uploadFile(file: MultipartFile): UploadRes {
        if (file.isEmpty || file.equals("") || file.originalFilename == null) {
            throw CustomException(HttpStatus.BAD_REQUEST, "올바르지 않은 파일")
        }

        val fileName = "uploads/" + UUID.randomUUID().toString().substring(0, 10) + file.originalFilename!!

        try {
            amazonS3.putObject(
                PutObjectRequest(s3Properties.s3bucket, fileName, file.inputStream, ObjectMetadata().apply { 
                    contentType = file.contentType
                    contentLength = file.size
                })
            )

            return UploadRes(
                url = amazonS3.getUrl(s3Properties.s3bucket, fileName).toString()
            )
        } catch (e: Exception) {
            throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "s3 업로드 실패")
        }
    }
}