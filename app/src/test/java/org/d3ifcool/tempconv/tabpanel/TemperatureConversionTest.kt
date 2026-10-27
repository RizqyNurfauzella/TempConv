package org.d3ifcool.tempconv.tabpanel

import org.junit.Assert.assertEquals
import org.junit.Test

class TemperatureConversionTest {
    @Test
    fun convertsKnownReferenceTemperatures() {
        val referencePoints = listOf(
            0.0 to Triple(32.0, 273.15, 0.0),
            100.0 to Triple(212.0, 373.15, 80.0),
            -40.0 to Triple(-40.0, 233.15, -32.0),
            -273.15 to Triple(-459.67, 0.0, -218.52)
        )
        for ((celsius, expected) in referencePoints) {
            val actual = convertCelsius(celsius)
            assertEquals("Fahrenheit at $celsius C", expected.first, actual.first, 1e-8)
            assertEquals("Kelvin at $celsius C", expected.second, actual.second, 1e-8)
            assertEquals("Reaumur at $celsius C", expected.third, actual.third, 1e-8)
        }
    }
}
