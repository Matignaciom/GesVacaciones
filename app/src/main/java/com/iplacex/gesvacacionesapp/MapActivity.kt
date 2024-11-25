package com.iplacex.gesvacacionesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de Osmdroid
        Configuration.getInstance().userAgentValue = packageName

        val mapView = MapView(this)
        mapView.setMultiTouchControls(true)
        setContentView(mapView)

        // Recuperar latitud y longitud de los extras del Intent
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        // Configurar el centro del mapa y añadir un marcador
        val startPoint = GeoPoint(latitude, longitude)
        mapView.controller.setZoom(15.0) // Nivel de zoom inicial
        mapView.controller.setCenter(startPoint)

        // Añadir un marcador en la posición
        val marker = Marker(mapView)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Ubicación Seleccionada"
        mapView.overlays.add(marker)
    }
}