package org.d3ifcool.tempconv

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.tempconv.ui.screen.MainScreen

@Composable
fun TempConvApp() {
    MainScreen(
        rememberNavController(),
    )
}