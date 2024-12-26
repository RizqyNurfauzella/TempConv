package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.model.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
        // Enable vertical scrolling
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Add scrolling here
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

            // Input for Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputCity,
                    onValueChange = { inputCity = it },
                    label = { Text("Enter Location") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon"
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Set ImeAction to Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { currentCity = inputCity } // Action when Done is pressed
                    )
                )
                IconButton(
                    onClick = { currentCity = inputCity },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Location",
                        tint = Color.White
                    )
                }
            }

            // Weather Card with Main Info
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = currentCity,
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Image(
                        painter = rememberAsyncImagePainter("https:${weatherIcon}"),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        text = "${temperature.toInt()}°C",
                        style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        weatherDescription,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Date: $date",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Wind",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                            Text(
                                "${windSpeed} km/h",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Humidity",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                            Text(
                                "${humidity}%",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Rain",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                            Text(
                                "${rainProbability}%",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
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
