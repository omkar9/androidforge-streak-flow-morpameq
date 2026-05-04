package com.androidforge.streakflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.androidforge.streakflow.presentation.addedit.AddEditHabitScreen
import com.androidforge.streakflow.presentation.details.HabitDetailsScreen
import com.androidforge.streakflow.presentation.home.HomeScreen
import com.androidforge.streakflow.presentation.onboarding.OnboardingScreen
import com.androidforge.streakflow.presentation.settings.SettingsScreen
import com.androidforge.streakflow.presentation.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.SplashScreen.route // Start with splash screen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditHabitScreen.route,
            arguments = listOf(navArgument("habitId") {
                type = NavType.LongType
                defaultValue = -1L // Indicates a new habit
                nullable = true
            })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getLong("habitId")
            AddEditHabitScreen(navController = navController, habitId = if (habitId == -1L) null else habitId)
        }
        composable(
            route = Screen.HabitDetailsScreen.route,
            arguments = listOf(navArgument("habitId") { type = NavType.LongType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getLong("habitId") ?: -1L
            HabitDetailsScreen(navController = navController, habitId = habitId)
        }
        composable(Screen.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }
    }
}