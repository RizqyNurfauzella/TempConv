package org.d3ifcool.tempconv.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
){
    val bottomNavigationItems = BottomButtonNavigation.entries.toTypedArray()

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
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
            NavigationBar {
                bottomNavigationItems.forEachIndexed { index, screen ->
                    val color = if (index == selectedTabIndex) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                    NavigationBarItem(
                        icon = {
                            val iconPainter = painterResource(id = screen.iconResId)
                            Icon(painter = iconPainter, contentDescription = null, tint = color)
                        },
                        label = { Text(stringResource(id = screen.textResId), color = color) },
                        selected = index == selectedTabIndex,
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
