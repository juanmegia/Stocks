package com.example.architectcoders.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteResultStockDetail(
    val meta: MetaProfile,
    val body: AssetProfile
)
@Serializable
data class MetaProfile(
    val version: String,
    val status: Int,
    val copywrite: String,
    val symbol: String,
    val processedTime: String,
    val modules: String
)
@Serializable
data class AssetProfile(
    val address1: String,
    val auditRisk: Int,
    val boardRisk: Int,
    val city: String,
    val companyOfficers: List<CompanyOfficer>,
    val compensationAsOfEpochDate: Int,
    val compensationRisk: Int,
    val country: String,
    val fullTimeEmployees: Int,
    val governanceEpochDate: Int,
    val industry: String,
    val longBusinessSummary: String,
    val maxAge: Int,
    val overallRisk: Int,
    val phone: String,
    val sector: String,
    val shareHolderRightsRisk: Int,
    val state: String,
    val website: String,
    val zip: String
)
@Serializable
data class CompanyOfficer(
    val name: String,
    val age: Int? = null,
    val title: String,
    val yearBorn: Int? = null,
    val fiscalYear: Int? = null,
    val totalPay: TotalPay? = null, // Ahora es opcional
    val exercisedValue: ExercisedValue? = null,
    val unexercisedValue: UnexercisedValue? = null)
@Serializable
data class ExercisedValue(
    val fmt: String?,
    val longFmt: String,
    val raw: Int
)
@Serializable
data class UnexercisedValue(
    val fmt: String?,
    val longFmt: String,
    val raw: Int
)
@Serializable
data class TotalPay(
    val fmt: String,
    val longFmt: String,
    val raw: Int
)