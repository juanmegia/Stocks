package com.example.architectcoders.data.datasource.remote

import androidx.room.TypeConverter
import com.example.architectcoders.data.CompanyOfficerSummary
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class Converters {

    private val json = Json { isLenient = true; ignoreUnknownKeys = true }

    // Convierte List<CompanyOfficerSummary> a una cadena JSON
    @TypeConverter
    fun fromCompanyOfficerSummaryList(value: List<CompanyOfficerSummary>): String {
        return json.encodeToString(value) // Convierte la lista a JSON
    }

    // Convierte una cadena JSON de vuelta a List<CompanyOfficerSummary>
    @TypeConverter
    fun toCompanyOfficerSummaryList(value: String): List<CompanyOfficerSummary> {
        return json.decodeFromString(value) // Convierte JSON de vuelta a lista
    }
}
