package com.iplacex.gesvacacionesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iplacex.gesvacacionesapp.data.AppDatabase
import com.iplacex.gesvacacionesapp.ui.theme.GesVacacionesAppTheme
import com.iplacex.gesvacacionesapp.viewmodel.ExchangeRateViewModel

class MainActivity : ComponentActivity() {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vacaciones-db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GesVacacionesAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val exchangeRateViewModel: ExchangeRateViewModel = viewModel()

                    AppNavGraph(
                        navController = navController,
                        placeDao = database.placeDao(),
                        exchangeRateViewModel = exchangeRateViewModel
                    )
                }
            }
        }
    }
}
