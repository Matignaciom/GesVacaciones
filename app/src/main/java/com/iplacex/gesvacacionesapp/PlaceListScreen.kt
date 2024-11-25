package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iplacex.gesvacacionesapp.data.PlaceDao
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun PlaceListScreen(placeDao: PlaceDao, navController: NavController) {
    val places = placeDao.getAllPlaces().collectAsState(initial = emptyList()).value
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (places.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(places) { place ->
                    PlaceItem(
                        place = place,
                        onClick = {
                            navController.navigate("place_detail/${place.id}")
                        },
                        onEdit = { updatedPlace ->
                            navController.navigate("place_edit/${updatedPlace.id}")
                        },
                        onDelete = { deletedPlace ->
                            coroutineScope.launch {
                                placeDao.deletePlace(deletedPlace)
                            }
                        }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No tienes lugares agregados. ¡Agrega uno!")
            }
        }

        Button(
            onClick = { navController.navigate("place_add") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Agregar Lugar")
        }
    }
}
