package org.d3ifcool.tempconv.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("Home")
    data object Temperature : Screen("Temperature")
    data object Setting : Screen("Setting")
}