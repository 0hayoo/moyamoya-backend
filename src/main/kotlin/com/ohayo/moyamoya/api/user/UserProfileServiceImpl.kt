package com.ohayo.moyamoya.api.user

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import com.ohayo.moyamoya.infra.s3.S3Properties
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("!test")
class UserProfileServiceImpl(
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Properties
) : UserProfileService {
    override fun getAvailableProfileImages(): List<String> {
        val urls = arrayListOf<String>()

        var result: ListObjectsV2Result
        val request = ListObjectsV2Request()
            .withBucketName(s3Properties.s3bucket)
            .withPrefix("profile-images/")

        do {
            result = amazonS3.listObjectsV2(request)
            for (objectSummary in result.objectSummaries) {
                if (objectSummary.size == 0L) continue

                val url = amazonS3.getUrl(s3Properties.s3bucket, objectSummary.key).toString()
                urls.add(url)
            }
            request.continuationToken = result.nextContinuationToken
        } while (result.isTruncated) // 페이지네이션 처리

        return urls
    }
}