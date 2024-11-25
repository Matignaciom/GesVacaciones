package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.iplacex.gesvacacionesapp.data.Place
import com.iplacex.gesvacacionesapp.data.PlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlaceAddScreen(placeDao: PlaceDao, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }
    var lodgingCost by remember { mutableStateOf("") }
    var transportCost by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var visitOrder by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Función para guardar el lugar
    fun savePlace() {
        // Validar los campos requeridos
        if (name.isNotEmpty() && imageUrl.isNotEmpty()) {
            val place = Place(
                name = name,
                visitOrder = visitOrder.toIntOrNull() ?: 0,
                comments = comments,
                lodgingCost = lodgingCost.toDoubleOrNull(),
                transportCost = transportCost.toDoubleOrNull(),
                latitude = latitude.toDoubleOrNull(),
                longitude = longitude.toDoubleOrNull(),
                imageUrl = imageUrl
            )

            // Guardar en la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                placeDao.insertPlace(place)

                // La operación de navegación se hace en el hilo principal
                withContext(Dispatchers.Main) {
                    navController.popBackStack() // Volver a la pantalla anterior
                }
            }
        } else {
            // Mostrar mensaje de error si los campos requeridos están vacíos
            errorMessage = "El nombre y la URL de la imagen son obligatorios."
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Agregar Lugar", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de texto
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del lugar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Imagen URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = comments,
            onValueChange = { comments = it },
            label = { Text("Comentarios") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lodgingCost,
            onValueChange = { lodgingCost = it },
            label = { Text("Costo de Alojamiento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = transportCost,
            onValueChange = { transportCost = it },
            label = { Text("Costo de Traslados") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = visitOrder,
            onValueChange = { visitOrder = it },
            label = { Text("Orden en la lista") },
            modifier = Modifier.fillMaxWidth()
        )

        // Mostrar mensaje de error
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar el lugar
        Button(
            onClick = {
                if (!isSaving) {
                    isSaving = true
                    savePlace()
                    isSaving = false
                }
            },
            enabled = !isSaving
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Guardar Lugar")
            }
        }
    }
}
