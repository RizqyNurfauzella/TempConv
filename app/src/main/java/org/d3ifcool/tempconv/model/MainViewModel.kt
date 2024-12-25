package org.d3ifcool.tempconv.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.d3ifcool.tempconv.api.HourlyWeather
import org.d3ifcool.tempconv.api.WeatherResponse
import org.d3ifcool.tempconv.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    init {
        viewModelScope.launch {
            delay(3000L)
            _isReady.value = true
        }
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
                            _weatherDescription.value =
                                weather.current.condition.text // Deskripsi cuaca
                            _temperature.value = weather.current.tempC // Suhu dalam Celcius
                            _date.value =
                                System.currentTimeMillis().toString() // Placeholder tanggal
                            _weatherIcon.value = weather.current.condition.icon // Ikon cuaca
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
    fun getHourlyWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            RetrofitInstance.weatherApiService.getWeather(apiKey, city)
                .enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful) {
                            val weather = response.body()

                            // Debugging: Print API Response
                            Log.d("API Response", "Weather: $weather")
                            Log.d("API Response", "Forecast: ${weather?.forecast}")

                            if (weather != null) {
                                // Perbaikan kode
                                val hourly = weather.forecast?.forecastday?.firstOrNull()?.hour ?: emptyList()
                                _hourlyWeather.value = hourly
                            } else {
                                _hourlyWeather.value = emptyList() // Tidak ada data
                            }
                        } else {
                            // Menangani kasus jika respons tidak berhasil
                            Log.d("API Response", "Failed to get weather data: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        t.printStackTrace()
                        _hourlyWeather.value = emptyList() // Gagal mengambil data
                        Log.d("API Response", "Error: ${t.message}")
                    }
                })
        }
    }
}
