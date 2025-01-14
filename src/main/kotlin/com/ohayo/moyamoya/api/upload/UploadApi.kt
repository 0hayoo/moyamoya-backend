package com.ohayo.moyamoya.api.upload

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("upload")
class UploadApi(
    private val uploadService: UploadService
) {
    @PostMapping
    fun uploadFile(@RequestParam("file") file: MultipartFile) = uploadService.uploadFile(file)
}