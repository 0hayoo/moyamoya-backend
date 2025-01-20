package com.ohayo.moyamoya.infra.neis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ohayo.moyamoya.core.school.SchoolEntity
import com.ohayo.moyamoya.core.school.SchoolRepository
import com.ohayo.moyamoya.core.school.SchoolType
import com.ohayo.moyamoya.global.CustomException
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class NeisSchoolClient(
    private val logger: KLogger,
    @Qualifier("neis")
    private val restClient: RestClient,
    private val neisProperties: NeisProperties,
    private val schoolRepository: SchoolRepository
)/*: CommandLineRunner*/ {
    
//    @Transactional
//    override fun run(vararg args: String?) {
//        run()
//    }
    
    @Transactional
//    @Scheduled(cron = "0 0 0 1 * *")
    fun run() {
        schoolRepository.deleteAll()
        schoolRepository.saveAll(getSchools())

        logger.info("Loading schools success")
    }

    private fun getSchools(): List<SchoolEntity> = Array(15) { i -> i + 1 }
        .mapNotNull { index ->
            restClient.get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("hub/schoolInfo")
                        .queryParam("KEY", neisProperties.apiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", index)
                        .queryParam("pSize", 1000) // Maximum value is 1000.
                        .build()
                }
                .retrieve()
                .body<String>()
                .let { it ?: throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Neis Error") }
                .let { jacksonObjectMapper().readValue<NeisSchoolRes>(it) }
                .schoolInfo
                ?.mapNotNull { it.row }
                ?.flatten()
        }
        .flatten()
        .mapNotNull {
            SchoolEntity(
                officeCode = it.atptOfcdcScCode,
                schoolCode = it.sdSchulCode,
                name = it.schulNm,
                type = it.schulKndScNm?.let { schulKndScNm ->
                    SchoolType.ofKorean(schulKndScNm) ?: tryParseSchoolType(schulKndScNm)
                } ?: return@mapNotNull null,
                cityName = it.lctnScNm,
                postalCode = it.orgRdnzc,
                address = it.orgRdnma,
                addressDetail = it.orgRdnda,
                phone = it.orgTelno,
                website = it.hmpgAdres,
                foundedAt = LocalDate.parse(it.fondYmd, DateTimeFormatter.ofPattern("yyyyMMdd")),
                anniversary = LocalDate.parse(it.foasMemrd, DateTimeFormatter.ofPattern("yyyyMMdd")),
            )
        }

    private fun tryParseSchoolType(text: String): SchoolType? = when (text) {
        in listOf(
            "재외한국학교(고)",
            "각종학교(고)",
            "방송통신고등학교",
            "고등공민학교",
            "고등기술학교",
            "평생학교(고)-2년6학기",
            "평생학교(고)-3년6학기"
        ) -> SchoolType.HIGH

        in listOf(
            "재외한국학교(중)",
            "각종학교 (중)",
            "방송통신중학교",
            "평생학교(중) - 3 년6학기",
            "평생학교(중) - 2 년6학기"
        ) -> SchoolType.MIDDLE

        in listOf(
            "재외한국학교(초)",
            "평생학교 (초) - 4 년12학기",
            "평생학교(초) - 3 년6학기",
            "각종학교(초)"
        ) -> SchoolType.ELEMENTARY

        else -> null
    }
}