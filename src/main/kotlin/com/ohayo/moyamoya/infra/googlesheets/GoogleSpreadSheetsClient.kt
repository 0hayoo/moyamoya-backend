package com.ohayo.moyamoya.infra.googlesheets

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class GoogleSpreadSheetsClient : CommandLineRunner {
    override fun run(vararg args: String?) {
        readData().forEach {
            println(it)
        }
    }

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

    fun readData(): List<List<Any>> {
        val response = sheets.spreadsheets().values()
            .get("1BmPBnpz8soA82pXCypGrWNbz3MkKTMdEWnJa_TXPG00", "A1:B10")
            .execute()
        return response.getValues() ?: emptyList()
    }
}