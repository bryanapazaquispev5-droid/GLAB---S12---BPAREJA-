package com.example.lab12

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.maps.CameraUpdateFactory

@Composable
fun MapScreen() {
   val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú
   val cameraPositionState = rememberCameraPositionState {
       position = CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
   }

   LaunchedEffect(Unit) {
       cameraPositionState.animate(
           update = CameraUpdateFactory.newLatLngZoom(LatLng(-16.2520984,-71.6836503), 12f), // Mover a Yura
           durationMs = 3000
       )
   }

   val locations = listOf(
       LatLng(-16.433415,-71.5442652), // JLByR
       LatLng(-16.4205151,-71.4945209), // Paucarpata
       LatLng(-16.3524187,-71.5675994) // Zamacola
   )

   Box(modifier = Modifier.fillMaxSize()) {
       // Añadir GoogleMap al layout
       GoogleMap(
           modifier = Modifier.fillMaxSize(),
           cameraPositionState = cameraPositionState
       ) {
           // Añadir marcador en Arequipa, Perú (Personalizado a color azul)
           Marker(
               state = rememberMarkerState(position = ArequipaLocation),
               title = "Arequipa, Perú",
               icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
           )

           // Añadir varios marcadores
           locations.forEach { location ->
               Marker(
                   state = rememberMarkerState(position = location),
                   title = "Ubicación",
                   snippet = "Punto de interés"
               )
           }
       }
   }
}
