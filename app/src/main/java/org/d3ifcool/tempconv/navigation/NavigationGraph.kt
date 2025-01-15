package org.d3ifcool.tempconv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3ifcool.tempconv.tabpanel.Home
import org.d3ifcool.tempconv.tabpanel.Setting
import org.d3ifcool.tempconv.tabpanel.Temperature

@Composable
fun NavigationGraph(navController: NavHostController, isDarkMode: Boolean) {
   NavHost(
      navController = navController,
      startDestination = Screen.Home.route
   ) {
      composable(route = Screen.Home.route) {
         Home(navController = navController, city = "Bandung", apiKey = "6339e42292e9448490e175455242512")
      }
      composable(route = Screen.Temperature.route) {
         Temperature(navController = navController)
      }
      composable(route = Screen.Setting.route) {
         Setting(navController = navController, isDarkMode = isDarkMode)
      }
   }
}

