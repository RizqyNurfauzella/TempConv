package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.tempconv.R

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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "TemperatureConv",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(onClick = { resetFields() }) {
                Icon(
                    painter = painterResource(R.drawable.refresh_icon),
                    contentDescription = "Refresh Weather",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Input Celsius
        TemperatureInputField(
            label = "Celsius",
            value = celsius,
            onValueChange = {
                celsius = it
                convertFromCelsius(it) // Perbarui konversi suhu setiap kali input berubah
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

        // Hasil konversi
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TemperatureOutputField(label = "Fahrenheit", value = fahrenheit)
            TemperatureOutputField(label = "Kelvin", value = kelvin)
            TemperatureOutputField(label = "Reaumur", value = reaumur)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureOutputField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {}, // Output tidak dapat diedit
        label = { Text(label) },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTemperature() {
    val navController = rememberNavController()
    Temperature(navController = navController)
}
