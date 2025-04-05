package com.example.architectcoders.data.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.architectcoders.data.database.remote.Converters
import kotlinx.serialization.Serializable

@Entity
data class StockDetailDao(
    @PrimaryKey
    val companySymbol: String,
    val industry: String,
    val sector: String,
    val businessSummary: String,
    val address: String,
    val phone: String,
    val website: String,
    val fullTimeEmployees: Int,
    @TypeConverters(Converters::class)
    val companyOfficersDao: List<CompanyOfficerSummaryDao>,
    val isFavorite: Boolean = false
)
@Serializable
data class CompanyOfficerSummaryDao(
    val name: String,
    val title: String,
    val totalPay: String?
)
