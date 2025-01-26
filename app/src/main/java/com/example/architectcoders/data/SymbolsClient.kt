package com.example.architectcoders.data

import com.example.architectcoders.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


object SymbolsClient {

    private const val BASE_URL = "https://yahoo-finance15.p.rapidapi.com/api/"

    private val json = Json{
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor { apiKeyAsHeader(it) }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(SymbolsService::class.java)

}
private fun apiKeyAsHeader(chain: Interceptor.Chain) = chain.proceed(
    chain.request().newBuilder()
       .addHeader("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com")
       .addHeader("x-rapidapi-key", BuildConfig.YAHOO_FINANCE_API_KEY)
       .build()
)