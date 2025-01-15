package org.d3ifcool.tempconv.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.d3ifcool.tempconv.R
import org.d3ifcool.tempconv.tabpanel.Home
import org.d3ifcool.tempconv.tabpanel.Setting
import org.d3ifcool.tempconv.tabpanel.Temperature

enum class BottomButtonNavigation(
    val iconResId: Int,
    val textResId: Int
) {
    HOME(R.drawable.home_btn, R.string.home),
    TEMPERATURE(R.drawable.temperature_btn, R.string.temperature),
    SETTING(R.drawable.setting_btn, R.string.settings)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    isDarkMode: Boolean,
) {
    val bottomNavigationItems = BottomButtonNavigation.entries.toTypedArray()

    var selectedTabIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState {
        bottomNavigationItems.size
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                bottomNavigationItems.forEachIndexed { index, screen ->
                    val isSelected = index == selectedTabIndex
                    NavigationBarItem(
                        icon = {
                            val iconPainter = painterResource(id = screen.iconResId)
                            Icon(
                                painter = iconPainter,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = screen.textResId),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                                selectedTabIndex = index
                            }
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                when (index) {
                    0 -> Home(navController, city = "Bandung", apiKey = "6339e42292e9448490e175455242512")
                    1 -> Temperature(navController)
                    2 -> Setting(navController, isDarkMode)
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(rememberNavController(), true)
}
