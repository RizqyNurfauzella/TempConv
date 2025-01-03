package org.d3ifcool.tempconv.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.tempconv.api.HourlyWeather
import org.d3ifcool.tempconv.api.WeatherResponse
import org.d3ifcool.tempconv.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _weatherDescription = MutableStateFlow("")
    val weatherDescription = _weatherDescription.asStateFlow()

    private val _temperature = MutableStateFlow(0f)
    val temperature = _temperature.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val _weatherIcon = MutableStateFlow("")
    val weatherIcon = _weatherIcon.asStateFlow()

    private val _hourlyWeather = MutableStateFlow<List<HourlyWeather>>(emptyList())
    val hourlyWeather = _hourlyWeather.asStateFlow()

    private val _windSpeed = MutableStateFlow(0f)
    val windSpeed = _windSpeed.asStateFlow()

    private val _humidity = MutableStateFlow(0)
    val humidity = _humidity.asStateFlow()

    private val _rainProbability = MutableStateFlow(0)
    val rainProbability = _rainProbability.asStateFlow()

    init {
        viewModelScope.launch {
            // Placeholder untuk menandai data siap
            _isReady.value = true
        }
    }

    // Fungsi untuk mendapatkan tanggal saat ini dalam format yang sesuai
    private fun getCurrentFormattedDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun getWeather(city: String, apiKey: String) {
        RetrofitInstance.weatherApiService.getWeather(apiKey, city)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { weather ->
                            _weatherDescription.value = weather.current.condition.text // Deskripsi cuaca
                            _temperature.value = weather.current.tempC // Suhu dalam Celcius
                            _date.value = getCurrentFormattedDate() // Perbarui tanggal
                            _weatherIcon.value = weather.current.condition.icon // Ikon cuaca
                            _windSpeed.value = weather.current.windSpeed // Kecepatan angin
                            _humidity.value = weather.current.humidity // Kelembapan
                            _rainProbability.value = weather.current.rainProbability // Peluang hujan
                            _isReady.value = true // Tandai data siap
                        }
                    } else {
                        Log.e("MainViewModel", "Failed to get weather data: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("MainViewModel", "Error: ${t.message}")
                    t.printStackTrace()
                }
            })
    }

    fun getHourlyWeather(city: String, apiKey: String) {
        RetrofitInstance.weatherApiService.getWeather(apiKey, city)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { weather ->
                            val hourly = weather.forecast?.forecastday?.firstOrNull()?.hour ?: emptyList()
                            _hourlyWeather.value = hourly
                            Log.d("MainViewModel", "Hourly weather updated: ${hourly.size} entries")
                        }
                    } else {
                        Log.e("MainViewModel", "Failed to get hourly weather data: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("MainViewModel", "Error: ${t.message}")
                    t.printStackTrace()
                    _hourlyWeather.value = emptyList()
                }
            })
    }
}
