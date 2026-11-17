package com.example.app_movil_tecnoventas.Modelo

data class ApiResponse<T>(val status: Boolean, val message: String, val data: T?)
