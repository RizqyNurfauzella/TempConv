package org.d3ifcool.tempconv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.d3ifcool.tempconv.model.MainViewModel
import org.d3ifcool.tempconv.ui.theme.TempConvTheme
import org.d3ifcool.tempconv.util.SettingsDataStore

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

            setContent {
                val dataStore = SettingsDataStore(LocalContext.current)
                val isDarkMode by dataStore.darkModeFlow.collectAsState(false)
                TempConvTheme(
                    darkTheme = isDarkMode
                ) {
                    TempConvApp(isDarkMode = isDarkMode)
                }
            }
        }
    }
