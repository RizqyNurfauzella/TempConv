package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.d3ifcool.tempconv.api.HourlyWeather

@Composable
fun HourlyWeather(hourlyWeather: List<HourlyWeather>) {
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
