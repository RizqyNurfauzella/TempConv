package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Temperature(navController: NavHostController) {
    // State untuk menyimpan input dan hasil konversi
    var celsius by remember { mutableStateOf("") }
    var fahrenheit by remember { mutableStateOf("") }
    var kelvin by remember { mutableStateOf("") }
    var reaumur by remember { mutableStateOf("") }

    // Fungsi untuk konversi suhu dari Celsius
    fun convertFromCelsius(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        fahrenheit = ((value * 9 / 5) + 32).toString()
        kelvin = (value + 273.15).toString()
        reaumur = (value * 4 / 5).toString()
    }

    // Fungsi untuk mereset semua input dan output
    fun resetFields() {
        celsius = ""
        fahrenheit = ""
        kelvin = ""
        reaumur = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tombol Refresh
        IconButton(
            onClick = { resetFields() },
            modifier = Modifier
                .align(Alignment.End)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = Color.White
            )
        }

        // Input Celsius
        Spacer(modifier = Modifier.height(16.dp))
        TemperatureInputField(
            label = "Celsius",
            value = celsius,
            onValueChange = {
                celsius = it
                convertFromCelsius(it) // Perbarui konversi suhu setiap kali input berubah
            }
        )

        // Hasil konversi
        TemperatureOutputField(label = "Fahrenheit", value = fahrenheit)
        TemperatureOutputField(label = "Kelvin", value = kelvin)
        TemperatureOutputField(label = "Reaumur", value = reaumur)
    }
}

@Composable
fun TemperatureInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun TemperatureOutputField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {}, // Output tidak dapat diedit
        label = { Text(label) },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
