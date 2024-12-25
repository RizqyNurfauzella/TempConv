package org.d3ifcool.tempconv.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    init {
        viewModelScope.launch {
            delay(3000L)
            _isReady.value = true
        }
    }

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            RetrofitInstance.weatherApiService.getWeather(apiKey, city)
                .enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful) {
                            val weather = response.body()
                            if (weather != null) {
                                _weatherDescription.value = weather.current.condition.text // Deskripsi cuaca
                                _temperature.value = weather.current.temp_c // Suhu dalam Celcius
                                _date.value = System.currentTimeMillis().toString() // Menggunakan waktu saat ini sebagai placeholder untuk tanggal
                                _weatherIcon.value = weather.current.condition.icon // Ikon cuaca
                            }
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }
}
