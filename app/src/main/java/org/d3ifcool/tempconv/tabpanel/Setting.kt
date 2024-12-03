package org.d3ifcool.tempconv.tabpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.util.SettingsDataStore

@Composable
fun Setting(
    navController: NavHostController,
    isDarkMode: Boolean
) {
    val dataStore = SettingsDataStore(LocalContext.current)
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        Text(
            "Settingan Pengguna",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )

        // Button settings
        ButtonSetting(
            iconResId = Icons.Filled.History,
            textResId = R.string.history,
            {}
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(
                    R.string.Arrow_Right
                )
            )
        }
        ButtonSetting(
            iconResId = Icons.Filled.Translate,
            textResId = R.string.language,
            {}
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(
                    R.string.Arrow_Right
                )
            )
        }

        ButtonSetting(
            iconResId =  if (isDarkMode) Icons.Filled.LightMode else Icons.Filled.DarkMode,
            textResId = if (isDarkMode) R.string.light_mode else R.string.dark_mode,
            {
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
                    } },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        ButtonSetting(
            iconResId = Icons.Filled.Share,
            textResId = R.string.share_app,
            {}
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(
                    R.string.Arrow_Right
                )
            )
        }
    }
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
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 11.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    iconResId,
                    contentDescription = stringResource(textResId),
                    modifier = Modifier.size(35.dp)
                )
                Text(text = stringResource(textResId))
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