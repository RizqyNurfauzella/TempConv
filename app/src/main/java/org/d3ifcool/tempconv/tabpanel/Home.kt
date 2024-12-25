package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.model.MainViewModel

@Composable
fun Home(navController: NavHostController, city: String, apiKey: String, mainViewModel: MainViewModel = viewModel()) {
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

    if (!isReady) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
        }
    } else {
        LaunchedEffect(Unit) {
            mainViewModel.getWeather(city, apiKey)
            mainViewModel.getHourlyWeather(city, apiKey)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Header with Location and Input
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Weather in $city",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = {
                    mainViewModel.getWeather(inputCity, apiKey)
                    mainViewModel.getHourlyWeather(inputCity, apiKey)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.refresh_icon),
                        contentDescription = "Refresh Weather",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Input for Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputCity,
                    onValueChange = { inputCity = it },
                    label = { Text("Enter Location") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    mainViewModel.getWeather(inputCity, apiKey)
                    mainViewModel.getHourlyWeather(inputCity, apiKey)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Location",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Weather Card with Main Info
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https:${weatherIcon}"),
                        contentDescription = "Weather Icon",
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "${temperature.toInt()}°C",
                        style = MaterialTheme.typography.displayMedium.copy(color = Color.White),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        weatherDescription,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Date: $date",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Wind: ${windSpeed} km/h", color = Color.White)
                        Text("Humidity: ${humidity}%", color = Color.White)
                        Text("Rain: ${rainProbability}%", color = Color.White)
                    }
                }
            }

            // Hourly Weather Section
            Text(
                "Hourly Weather",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
            if (hourlyWeather.isEmpty()) {
                Text("Hourly weather data is not available.", color = Color.Gray)
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    hourlyWeather.forEach { weather ->
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.width(100.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(weather.time, style = TextStyle(color = Color.White))
                                Image(
                                    painter = rememberAsyncImagePainter("https:${weather.icon}"),
                                    contentDescription = "Weather Icon",
                                    modifier = Modifier.size(48.dp)
                                )
                                Text(
                                    "${weather.temperature.toInt()}°C",
                                    style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePrev() {
    Home(navController = rememberNavController(), city = "Bandung", apiKey = "6339e42292e9448490e175455242512")
}
