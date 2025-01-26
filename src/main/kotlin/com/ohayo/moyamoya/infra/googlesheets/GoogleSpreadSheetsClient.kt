package com.ohayo.moyamoya.infra.googlesheets

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.ohayo.moyamoya.common.ListUtil
import com.ohayo.moyamoya.infra.googlesheets.value.GSSQuestionRes
import com.ohayo.moyamoya.infra.googlesheets.value.GSSSubjectRes
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class GoogleSpreadSheetsClient /*: CommandLineRunner*/ {
//    override fun run(vararg args: String?) {
//        readSubjects().forEach {
//            println("Subject: ${it.subject}")
//            println(it.questions.joinToString(", ") { cell -> cell.question })
//        }
//    }

    private val sheets: Sheets = run {
        val credentials: GoogleCredentials =
            GoogleCredentials.fromStream(ClassPathResource("secret/moyamoya-googlespreadsheets.json").inputStream)
                .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))
        Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            HttpCredentialsAdapter(credentials)
        )
            .setApplicationName("Google Sheets Application")
            .build()
    }

    fun readSubjects(): List<GSSSubjectRes> = sheets.spreadsheets().values()
        .get("1BmPBnpz8soA82pXCypGrWNbz3MkKTMdEWnJa_TXPG00", "A1:AA50")
        .execute()
        .getValues()
        .run { this ?: emptyList() }
        .mapNotNull { row ->
            row.mapNotNull row@{
                val value = it.toString()
                if (value.isEmpty()) return@row null
                value
            }
        }.run {
            ListUtil.transpose(this)
        }.mapNotNull { row ->
            println(row)
            GSSSubjectRes(
                title = row.firstOrNull() ?: return@mapNotNull null,
                questions = row.slice(1..<row.size).map { cell ->
                    GSSQuestionRes(
                        question = cell
                    )
                }
            )
        }
}