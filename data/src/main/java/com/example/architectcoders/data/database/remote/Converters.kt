package com.example.architectcoders.data.database.remote

import androidx.room.TypeConverter
import com.example.architectcoders.data.database.local.CompanyOfficerSummaryDao
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

class Converters {

    private val json = Json { isLenient = true; ignoreUnknownKeys = true }

    // Convierte List<CompanyOfficerSummary> a una cadena JSON
    @TypeConverter
    fun fromCompanyOfficerSummaryList(value: List<CompanyOfficerSummaryDao>): String {
        return json.encodeToString(value) // Convierte la lista a JSON
    }

    // Convierte una cadena JSON de vuelta a List<CompanyOfficerSummary>
    @TypeConverter
    fun toCompanyOfficerSummaryList(value: String): List<CompanyOfficerSummaryDao> {
        return json.decodeFromString(value) // Convierte JSON de vuelta a lista
    }
}
