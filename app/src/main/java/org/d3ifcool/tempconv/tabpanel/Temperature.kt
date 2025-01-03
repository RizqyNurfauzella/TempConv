package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    fun convertFromCelsius(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        fahrenheit = ((value * 9 / 5) + 32).toString()
        kelvin = (value + 273.15).toString()
        reaumur = (value * 4 / 5).toString()
    }

    fun convertFromFahrenheit(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        celsius = ((value - 32) * 5 / 9).toString()
        kelvin = (((value - 32) * 5 / 9) + 273.15).toString()
        reaumur = ((value - 32) * 4 / 9).toString()
    }

    fun convertFromKelvin(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        celsius = (value - 273.15).toString()
        fahrenheit = (((value - 273.15) * 9 / 5) + 32).toString()
        reaumur = ((value - 273.15) * 4 / 5).toString()
    }

    fun convertFromReaumur(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        celsius = (value * 5 / 4).toString()
        fahrenheit = ((value * 9 / 4) + 32).toString()
        kelvin = ((value * 5 / 4) + 273.15).toString()
    }

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
                    contentDescription = "Reset Fields",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        TemperatureInputField(
            label = "Celsius",
            value = celsius,
            onValueChange = {
                celsius = it
                if (it.isNotEmpty()) convertFromCelsius(it)
            }
        )

        TemperatureInputField(
            label = "Fahrenheit",
            value = fahrenheit,
            onValueChange = {
                fahrenheit = it
                if (it.isNotEmpty()) convertFromFahrenheit(it)
            }
        )

        TemperatureInputField(
            label = "Kelvin",
            value = kelvin,
            onValueChange = {
                kelvin = it
                if (it.isNotEmpty()) convertFromKelvin(it)
            }
        )

        TemperatureInputField(
            label = "Reaumur",
            value = reaumur,
            onValueChange = {
                reaumur = it
                if (it.isNotEmpty()) convertFromReaumur(it)
            }
        )
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
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewTemperature() {
    val navController = rememberNavController()
    Temperature(navController = navController)
}
