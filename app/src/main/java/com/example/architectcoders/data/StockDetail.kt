package com.example.architectcoders.data

data class StockDetail(
    val companySymbol: String,
    val industry: String,
    val sector: String,
    val businessSummary: String,
    val address: String,
    val phone: String,
    val website: String,
    val fullTimeEmployees: Int,
    val companyOfficers: List<CompanyOfficerSummary>
)

data class CompanyOfficerSummary(
    val name: String,
    val title: String,
    val totalPay: String?
)
