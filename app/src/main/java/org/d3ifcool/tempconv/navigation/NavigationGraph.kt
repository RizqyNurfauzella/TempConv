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
fun NavigationGraph(navController: NavHostController = rememberNavController(), isDarkMode: Boolean) {
   NavHost(
      navController = navController,
      startDestination = Screen.Home.route
   ) {
      // Main route
      composable(route = Screen.Home.route) {
         Home(navController)
      }
      composable(route = Screen.Temperature.route) {
         Temperature(navController)
      }
      composable(route = Screen.Setting.route) {
         Setting(navController, isDarkMode)
      }
   }
}
