package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.iplacex.gesvacacionesapp.data.Place
import com.iplacex.gesvacacionesapp.data.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlaceEditScreen(placeId: Long, placeDao: PlaceDao, navController: NavHostController) {
    var updatedPlace by remember { mutableStateOf<Place?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Obtener los detalles del lugar
    LaunchedEffect(placeId) {
        placeDao.getPlaceById(placeId).collect { place ->
            updatedPlace = place
        }
    }

    // Si no se han encontrado detalles para el lugar, mostrar un mensaje
    if (updatedPlace == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No se pudo cargar el lugar.")
        }
    } else {
        // Verificar si el lugar no es nulo
        updatedPlace?.let { place ->
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = place.name,
                    onValueChange = { updatedPlace = updatedPlace?.copy(name = it) },
                    label = { Text("Nombre del lugar") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.imageUrl ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(imageUrl = it) },
                    label = { Text("Imagen URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.comments ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(comments = it) },
                    label = { Text("Comentarios") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.lodgingCost?.toString() ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(lodgingCost = it.toDoubleOrNull()) },
                    label = { Text("Costo de Alojamiento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.transportCost?.toString() ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(transportCost = it.toDoubleOrNull()) },
                    label = { Text("Costo de Traslados") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.latitude?.toString() ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(latitude = it.toDoubleOrNull()) },
                    label = { Text("Latitud") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = place.longitude?.toString() ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(longitude = it.toDoubleOrNull()) },
                    label = { Text("Longitud") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = updatedPlace?.visitOrder?.toString() ?: "",
                    onValueChange = { updatedPlace = updatedPlace?.copy(visitOrder = it.toIntOrNull() ?: 0) },
                    label = { Text("Orden en la lista") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para actualizar el lugar
                Button(
                    onClick = {
                        updatedPlace?.let { updatedPlace ->
                            if (!isSaving) {
                                isSaving = true
                                coroutineScope.launch {
                                    placeDao.updatePlace(updatedPlace)
                                    withContext(Dispatchers.Main) {
                                        isSaving = false
                                        // Regresar a la pantalla anterior después de actualizar
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                    },
                    enabled = !isSaving
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Actualizar Lugar")
                    }
                }
            }
        }
    }
}
