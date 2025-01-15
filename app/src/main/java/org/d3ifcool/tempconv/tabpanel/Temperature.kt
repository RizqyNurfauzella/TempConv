package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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

    fun convertFromCelsius(input: String) {
        val value = input.toFloatOrNull() ?: 0f
        fahrenheit = ((value * 9 / 5) + 32).toString()
        kelvin = (value + 273.15).toString()
        reaumur = (value * 4 / 5).toString()
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
                    contentDescription = "Refresh Weather",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        TemperatureInputField(
            label = "Celsius",
            value = celsius,
            onValueChange = {
                celsius = it
                convertFromCelsius(it)
            }
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )


        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TemperatureOutputField(
                label = "Fahrenheit",
                value = fahrenheit,
            )
            TemperatureOutputField(
                label = "Kelvin",
                value = kelvin,
            )
            TemperatureOutputField(
                label = "Reaumur",
                value = reaumur,
            )
        }

        Spacer(modifier = Modifier.weight(1f))
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
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    )

}

@Composable
fun TemperatureOutputField(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Thermostat,
                contentDescription = "$label Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "$label: $value",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTemperature() {
    val navController = rememberNavController()
    Temperature(navController = navController)
}
