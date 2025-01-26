package com.example.architectcoders.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteResult(
    val body: List<RemoteStock>,
    val meta: Meta
)
@Serializable
data class RemoteStock(
    val lastsale: String,
    val marketCap: String,
    val name: String,
    val netchange: String,
    val pctchange: String,
    val symbol: String
)
@Serializable
data class Meta(
    val copywrite: String,
    val headers: Headers,
    val status: Int,
    val totalrecords: Int,
    val version: String
)
@Serializable
data class Headers(
    val lastsale: String,
    val marketCap: String,
    val name: String,
    val netchange: String,
    val pctchange: String,
    val symbol: String
)