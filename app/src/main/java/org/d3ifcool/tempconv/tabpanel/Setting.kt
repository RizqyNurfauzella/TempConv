package org.d3ifcool.tempconv.tabpanel

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.util.SettingsDataStore
import java.util.Locale

@Composable
fun Setting(
    navController: NavHostController,
    isDarkMode: Boolean
) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)

    // State untuk memicu recomposition
    val languageState = remember { mutableStateOf(Locale.getDefault().language) }
    val isLoading = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Button settings
            ButtonSetting(
                iconResId = Icons.Filled.History,
                textResId = R.string.history,
                onClick = {}
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.Arrow_Right),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Dropdown for language selection with animation
            val expanded = remember { mutableStateOf(false) }
            ButtonSetting(
                iconResId = Icons.Filled.Translate,
                textResId = R.string.language,
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                AnimatedVisibility(
                    visible = expanded.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                changeLanguage(context, Locale("en"), languageState, isLoading)
                                expanded.value = false
                            },
                            text = { Text("English") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                changeLanguage(context, Locale("id"), languageState, isLoading)
                                expanded.value = false
                            },
                            text = { Text("Bahasa Indonesia") }
                        )
                    }
                }
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.Arrow_Right),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Show loading animation when changing language
            if (isLoading.value) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            ButtonSetting(
                iconResId = if (isDarkMode) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                textResId = if (isDarkMode) R.string.light_mode else R.string.dark_mode,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveDarkMode(!isDarkMode)
                    }
                }
            ) {
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveDarkMode(!isDarkMode)
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            ButtonSetting(
                iconResId = Icons.Filled.Share,
                textResId = R.string.share_app,
                onClick = {}
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.Arrow_Right),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

fun changeLanguage(
    context: Context,
    locale: Locale,
    languageState: androidx.compose.runtime.MutableState<String>,
    isLoading: androidx.compose.runtime.MutableState<Boolean>
) {
    isLoading.value = true
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000) // Simulate loading delay
        setLocale(context, locale)
        languageState.value = locale.language
        isLoading.value = false
    }
}

fun setLocale(context: Context, locale: Locale) {
    val config = Configuration(context.resources.configuration).apply {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            this.locale = locale
        }
    }
    @Suppress("DEPRECATION")
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

@Composable
fun ButtonSetting(
    iconResId: ImageVector,
    textResId: Int,
    onClick: () -> Unit,
    trailIcon: @Composable () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    iconResId,
                    contentDescription = stringResource(textResId),
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(textResId),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            trailIcon()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPrev() {
    Setting(rememberNavController(), true)
}
