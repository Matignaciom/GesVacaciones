package com.iplacex.gesvacacionesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iplacex.gesvacacionesapp.data.PlaceDao
import com.iplacex.gesvacacionesapp.viewmodel.ExchangeRateViewModel

@Composable
fun AppNavGraph(navController: NavHostController, placeDao: PlaceDao, exchangeRateViewModel: ExchangeRateViewModel) {
    NavHost(navController, startDestination = "place_list") {
        composable("place_list") {
            // Pantalla de lista de lugares
            PlaceListScreen(placeDao = placeDao, navController = navController)
        }
        composable("place_detail/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toLong() ?: 0
            PlaceDetailScreen(placeId = placeId, placeDao = placeDao)
        }
        composable("place_edit/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toLong() ?: 0
            PlaceEditScreen(placeId = placeId, placeDao = placeDao, navController = navController)
        }
        composable("place_add") {
            // Pantalla de agregar lugar
            PlaceAddScreen(placeDao = placeDao, navController = navController)
        }
        composable("main_screen") {
            // Pantalla principal
            MainScreen(navController = navController, placeDao = placeDao, exchangeRateViewModel = exchangeRateViewModel)
        }
    }
}
