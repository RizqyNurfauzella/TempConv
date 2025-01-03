package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.model.MainViewModel

@Composable
fun Home(
    navController: NavHostController,
    city: String,
    apiKey: String,
    mainViewModel: MainViewModel = viewModel()
) {
    val isReady by mainViewModel.isReady.collectAsState()
    val weatherDescription by mainViewModel.weatherDescription.collectAsState()
    val temperature by mainViewModel.temperature.collectAsState()
    val date by mainViewModel.date.collectAsState()
    val weatherIcon by mainViewModel.weatherIcon.collectAsState()
    val hourlyWeather by mainViewModel.hourlyWeather.collectAsState()
    val windSpeed by mainViewModel.windSpeed.collectAsState()
    val humidity by mainViewModel.humidity.collectAsState()
    val rainProbability by mainViewModel.rainProbability.collectAsState()

    var inputCity by remember { mutableStateOf(city) }
    var currentCity by remember { mutableStateOf(city) }

    LaunchedEffect(currentCity) {
        mainViewModel.getWeather(currentCity, apiKey)
        mainViewModel.getHourlyWeather(currentCity, apiKey)
    }

    if (!isReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Loading...",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with Location and Input
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Weather in $currentCity",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = {
                    mainViewModel.getWeather(currentCity, apiKey)
                    mainViewModel.getHourlyWeather(currentCity, apiKey)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.refresh_icon),
                        contentDescription = "Refresh Weather",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            // LocationInput
            LocationInput(
                inputCity = inputCity,
                onInputChange = { inputCity = it },
                onSearch = { currentCity = inputCity }
            )
            // WeatherCard
            WeatherCard(
                currentCity = currentCity,
                weatherIcon = weatherIcon,
                temperature = temperature.toInt(),
                weatherDescription = weatherDescription,
                date = date,
                windSpeed = windSpeed.toString(), // Konversi ke String
                humidity = humidity.toString(), // Konversi ke String
                rainProbability = rainProbability.toString() // Konversi ke String
            )
            // HourlyWeather
            HourlyWeather(hourlyWeather = hourlyWeather)
        }
    }
}

@Preview
@Composable
fun HomePrev() {
    Home(navController = rememberNavController(), city = "Bandung", apiKey = "6339e42292e9448490e175455242512")
}
