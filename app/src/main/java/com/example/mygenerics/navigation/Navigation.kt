package com.example.mygenerics.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember ( this) {
        navController.getBackStackEntry (navGraphRoute)
    }

    return hiltViewModel (parentEntry)
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
){

    val navController = rememberNavController()
    NavHost(

        modifier = modifier,
        navController = navController, startDestination = "route1" ){

        navigation(startDestination = "screen1",route = "route1"){
            composable("screen1"){ backStackEntry->
                Screen1(onNavigateToScreen2 = { navController.navigate("screen2")},
                    viewModel = backStackEntry.sharedViewModel(navController = navController))}

            composable("screen2") { backStackEntry->
                Screen2(onNavigateToScreen1 = {
                    navController.popBackStack("route1", inclusive = true)
                    navController.navigate("home")
                },
                    viewModel = backStackEntry.sharedViewModel(navController = navController)
                )}
        }
//        composable("screen1"){ Screen1(onNavigateToScreen2 = { navController.navigate("screen2") })}
//        composable("screen2"){ Screen2(onNavigateToScreen1 = { navController.navigate("screen1") },)}
        composable("home"){ HomeScreen()}
    }
}
