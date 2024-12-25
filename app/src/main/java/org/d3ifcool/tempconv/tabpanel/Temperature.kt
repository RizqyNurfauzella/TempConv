package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)  // Menambahkan anotasi OptIn untuk API eksperimental
@Composable
fun Temperature(navController: NavHostController) {
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
        // TopAppBar
        TopAppBar(
            title = {
                Text(
                    text = "TempConv",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            actions = {
                IconButton(onClick = { resetFields() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = Color.White
                    )
                }
            },
            modifier = Modifier.padding(bottom = 16.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )

        // Input Celsius
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

@Preview(showBackground = true)
@Composable
fun PreviewTemperature() {
    // Membuat navController sementara untuk pratinjau
    val navController = rememberNavController()
    Temperature(navController = navController)
}

