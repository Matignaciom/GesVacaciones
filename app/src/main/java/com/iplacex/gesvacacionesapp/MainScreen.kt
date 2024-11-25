package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iplacex.gesvacacionesapp.data.PlaceDao
import com.iplacex.gesvacacionesapp.viewmodel.ExchangeRateViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    placeDao: PlaceDao,
    exchangeRateViewModel: ExchangeRateViewModel
) {
    // Observamos el estado de los datos de la tasa de cambio
    val exchangeRateState = exchangeRateViewModel.exchangeRate.collectAsState(initial = null)

    Surface(modifier = Modifier.padding(16.dp)) {
        Column {
            Text("Welcome to the Main Screen", style = MaterialTheme.typography.headlineMedium)

            // Mostrar la tasa de cambio si está disponible
            val exchangeRateResponse = exchangeRateState.value
            if (exchangeRateResponse != null) {
                val dolarValor = exchangeRateResponse.dolar.valor
                Text("Tasa de cambio del Dólar: $dolarValor")
            } else {
                Text("Cargando tasa de cambio...")
            }

            // Si hay lugares disponibles, los mostramos
            val places = placeDao.getAllPlaces().collectAsState(initial = emptyList()).value
            if (places.isEmpty()) {
                Text("No places available")
            } else {
                places.forEach { place ->
                    Text(
                        text = place.name,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("place_detail/${place.id}")
                            }
                    )
                }
            }

            // Ejemplo de navegación a la pantalla de lista de lugares
            Button(onClick = {
                navController.navigate("place_list")
            }) {
                Text("Go to Place List")
            }
        }
    }
}
