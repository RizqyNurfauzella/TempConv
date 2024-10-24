package org.d3ifcool.tempconv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.d3ifcool.tempconv.ui.screen.MainScreen
import org.d3ifcool.tempconv.ui.theme.TempConvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TempConvTheme {
                MainScreen()
            }
        }
    }
}