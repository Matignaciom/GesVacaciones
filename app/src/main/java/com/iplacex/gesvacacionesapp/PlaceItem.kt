package com.iplacex.gesvacacionesapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iplacex.gesvacacionesapp.data.Place
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@Composable
fun PlaceItem(place: Place, onClick: (Place) -> Unit, onEdit: (Place) -> Unit, onDelete: (Place) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick(place) }
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            place.imageUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Place Image",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(place.name, style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold), maxLines = 1)

                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("Costo por noche: ${place.lodgingCost ?: "No disponible"}")
                    Text("Traslado: ${place.transportCost ?: "No disponible"}")
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { openMap(place, context) }) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Ver mapa", modifier = Modifier.size(24.dp))
                    }
                    IconButton(onClick = { onEdit(place) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar lugar", modifier = Modifier.size(24.dp))
                    }
                    IconButton(onClick = { onDelete(place) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar lugar", modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

fun openMap(place: Place, context: Context) {
    if (place.latitude != null && place.longitude != null) {
        val intent = Intent(context, MapActivity::class.java)
        intent.putExtra("latitude", place.latitude)
        intent.putExtra("longitude", place.longitude)
        context.startActivity(intent)
    } else {
        // Muestra un mensaje si no hay coordenadas disponibles
        Toast.makeText(context, "Coordenadas no disponibles para este lugar", Toast.LENGTH_SHORT).show()
    }
}
