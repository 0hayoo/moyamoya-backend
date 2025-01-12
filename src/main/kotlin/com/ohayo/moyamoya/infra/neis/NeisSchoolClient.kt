package com.ohayo.moyamoya.infra.neis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ohayo.moyamoya.core.SchoolEntity
import com.ohayo.moyamoya.core.SchoolRepository
import com.ohayo.moyamoya.core.SchoolType
import com.ohayo.moyamoya.global.CustomException
import mu.KLogger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
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
) { 
    @Transactional
    @Scheduled(cron = "0 0 0 1 * *")
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
        .map {
            SchoolEntity(
                officeCode = it.atptOfcdcScCode,
                schoolCode = it.sdSchulCode,
                name = it.schulNm,
                type = it.schulKndScNm?.let(SchoolType::ofKorean),
                cityName = it.lctnScNm,
                postalCode = it.orgRdnzc,
                address = it.orgRdnma,
                addressDetail = it.orgRdnda,
                phone = it.orgTelno,
                website = it.hmpgAdres,
                createdAt = LocalDate.parse(it.fondYmd, DateTimeFormatter.ofPattern("yyyyMMdd")),
                anniversary = LocalDate.parse(it.foasMemrd, DateTimeFormatter.ofPattern("yyyyMMdd")),
            )
        }
}
