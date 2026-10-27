package org.d3ifcool.tempconv.tabpanel

internal fun convertCelsius(celsius: Double): Triple<Double, Double, Double> =
    Triple(celsius * 9 / 5 + 32, celsius + 273.15, celsius * 4 / 5)
