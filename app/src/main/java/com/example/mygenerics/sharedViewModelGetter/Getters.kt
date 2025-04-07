package com.example.mygenerics.sharedViewModelGetter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
inline fun <reified VM : ViewModel> activityViewModel(): VM {
    val context = LocalContext.current
    val viewModelStoreOwner = context as? ViewModelStoreOwner
        ?: error("Context is not a ViewModelStoreOwner")
    return hiltViewModel(viewModelStoreOwner)
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember ( this) {
        navController.getBackStackEntry (navGraphRoute)
    }

    return viewModel (parentEntry)
}


