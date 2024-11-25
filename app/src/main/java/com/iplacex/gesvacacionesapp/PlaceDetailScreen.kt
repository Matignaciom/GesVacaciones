package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.iplacex.gesvacacionesapp.data.Place
import com.iplacex.gesvacacionesapp.data.PlaceDao

@Composable
fun PlaceDetailScreen(placeId: Long, placeDao: PlaceDao) {
    var place by remember { mutableStateOf<Place?>(null) }

    // Obtener los detalles del lugar desde la base de datos
    LaunchedEffect(placeId) {
        placeDao.getPlaceById(placeId).collect { fetchedPlace ->
            place = fetchedPlace
        }
    }

    // Pantalla de carga si el lugar aún no está disponible
    if (place == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Mostrar los detalles del lugar
        place?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Título del lugar
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Imagen del lugar
                it.imageUrl?.let { imageUrl ->
                    val image: Painter = rememberAsyncImagePainter(imageUrl)
                    Image(
                        painter = image,
                        contentDescription = "Imagen de ${it.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información de costos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Costo x Noche",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = it.lodgingCost?.let { "$$it" } ?: "No disponible",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Column {
                        Text(
                            text = "Traslados",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = it.transportCost?.let { "$$it" } ?: "No disponible",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comentarios
                Text(
                    text = "Comentarios:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = it.comments ?: "Sin comentarios",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Acciones (íconos de cámara, edición y eliminación)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Acción para tomar foto */ }) {
                        Icon(
                            painter = rememberAsyncImagePainter(android.R.drawable.ic_menu_camera),
                            contentDescription = "Tomar Foto"
                        )
                    }
                }
            }
        }
    }
}
