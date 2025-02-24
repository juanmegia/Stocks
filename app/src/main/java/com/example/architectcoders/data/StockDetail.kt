package com.example.architectcoders.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockDetail(
    @PrimaryKey
    val companySymbol: String,
    val industry: String,
    val sector: String,
    val businessSummary: String,
    val address: String,
    val phone: String,
    val website: String,
    val fullTimeEmployees: Int,
    val companyOfficers: List<CompanyOfficerSummary>,
    val isFavorite: Boolean = false
)

data class CompanyOfficerSummary(
    val name: String,
    val title: String,
    val totalPay: String?
)
