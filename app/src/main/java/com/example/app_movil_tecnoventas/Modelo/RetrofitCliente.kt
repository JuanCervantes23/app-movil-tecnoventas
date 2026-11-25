package com.example.app_movil_tecnoventas.Modelo

import com.example.app_movil_tecnoventas.InterfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitCliente {
    const val BASE_URL = "https://equipo4.grupoc.io/api/"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    val apiService: ApiService by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType = contentType))
            .build()
            .create(ApiService::class.java)
    }

    val api: ApiService
        get() = apiService
}