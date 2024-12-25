package org.d3ifcool.tempconv.api

import com.google.gson.annotations.SerializedName // Tambahkan impor untuk anotasi

data class WeatherResponse(
    val current: CurrentWeather,
    val forecast: Forecast? = null // Buat nullable untuk mencegah crash
)

data class CurrentWeather(
    @SerializedName("temp_c") val tempC: Float, // Gunakan camelCase dengan pemetaan ke nama asli JSON
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)

// Data untuk forecast
data class Forecast(
    val forecastday: List<ForecastDay>
)

// Data untuk satu hari dalam forecast
data class ForecastDay(
    val hour: List<HourlyWeather>
)

// Data untuk cuaca per jam
data class HourlyWeather(
    val time: String,
    @SerializedName("temp_c") val tempC: Float, // Gunakan camelCase dengan pemetaan
    val condition: Condition
) {
    val icon: String
        get() = condition.icon
    val temperature: Float
        get() = tempC
}
