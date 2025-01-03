package org.d3ifcool.tempconv.api

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: CurrentWeather,
    val forecast: Forecast? = null // Forecast nullable untuk mencegah crash jika tidak ada data
)

data class CurrentWeather(
    @SerializedName("temp_c") val tempC: Float, // Suhu saat ini dalam Celcius
    val condition: Condition,
    @SerializedName("wind_kph") val windSpeedKph: Float, // Kecepatan angin dalam KPH
    @SerializedName("humidity") val humidity: Int, // Kelembapan dalam persen
    @SerializedName("precip_mm") val precipitationMm: Float // Curah hujan dalam mm
) {
    val windSpeed: Float
        get() = windSpeedKph // Alias untuk kecepatan angin
    val rainProbability: Int
        get() = if (precipitationMm > 0) 100 else 0 // Sederhanakan probabilitas hujan
}

data class Condition(
    val text: String, // Deskripsi cuaca
    val icon: String // Ikon cuaca
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
    val time: String, // Waktu cuaca per jam
    @SerializedName("temp_c") val tempC: Float, // Suhu dalam Celcius
    val condition: Condition
) {
    val icon: String
        get() = condition.icon
    val temperature: Float
        get() = tempC
}
