package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.model.MainViewModel

@Composable
fun Home(navController: NavHostController, city: String, apiKey: String, mainViewModel: MainViewModel = viewModel()) {
    // Mengambil data dari ViewModel
    val isReady by mainViewModel.isReady.collectAsState()
    val weatherDescription by mainViewModel.weatherDescription.collectAsState()
    val temperature by mainViewModel.temperature.collectAsState()
    val date by mainViewModel.date.collectAsState()
    val weatherIcon by mainViewModel.weatherIcon.collectAsState()
    val hourlyWeather by mainViewModel.hourlyWeather.collectAsState()

    var inputCity by remember { mutableStateOf(city) }

    // Memastikan aplikasi sedang menunggu data cuaca
    if (!isReady) {
        Text("Loading...", modifier = Modifier.fillMaxSize(), color = Color.Gray)
    } else {
        // Memanggil data cuaca dan data cuaca per jam saat pertama kali
        LaunchedEffect(Unit) {
            mainViewModel.getWeather(city, apiKey)
            mainViewModel.getHourlyWeather(city, apiKey)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            // TopAppBar dengan Nama Aplikasi dan Button Refresh
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "tempConv",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = {
                    mainViewModel.getWeather(inputCity, apiKey)
                    mainViewModel.getHourlyWeather(inputCity, apiKey)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.refresh_icon),
                        contentDescription = "Refresh Weather"
                    )
                }
            }

            // Input Kota Baru
            TextField(
                value = inputCity,
                onValueChange = { inputCity = it },
                label = { Text("Enter City") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    mainViewModel.getWeather(inputCity, apiKey)
                    mainViewModel.getHourlyWeather(inputCity, apiKey)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Get Weather")
            }

            // Menampilkan data cuaca untuk lokasi
            Text(
                inputCity,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            // Weather Card
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4D6DE3)
                ),
                shape = RoundedCornerShape(40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 12.dp)
                        .sizeIn(minHeight = 200.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Weather Icon and Text
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .sizeIn(maxWidth = 100.dp)
                                    .padding(bottom = 8.dp)
                            ) {
                                // Using Coil to load image from the weather icon URL
                                Image(
                                    painter = rememberAsyncImagePainter("https:${weatherIcon}"),
                                    contentDescription = "Cuaca $weatherDescription",
                                    modifier = Modifier.aspectRatio(1f)
                                )
                            }
                            Text(
                                weatherDescription,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                        }

                        // Temperature and Date
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 80.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("${temperature.toInt()}")
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 70.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("°")
                                    }
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = 40.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("C")
                                    }
                                },
                                fontSize = 80.sp,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.background,
                            )
                            Text(
                                date,
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.background,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.End,
                                )
                            )
                        }
                    }
                }
            }

            // Cuaca Tiap Jam
            Text(
                "Hourly Weather",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Menampilkan pesan jika data hourlyWeather kosong
            if (hourlyWeather.isEmpty()) {
                Text("Hourly weather data is not available.", color = Color.Gray)
            } else {
                // Render data hourlyWeather
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    hourlyWeather.forEach { weather ->
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(4.dp)
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