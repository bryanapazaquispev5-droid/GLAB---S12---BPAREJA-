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
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import androidx.compose.ui.graphics.Color
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@Composable
fun MapScreen() {
   val ArequipaLocation = LatLng(-16.4040102, -71.559611) // Arequipa, Perú
   val cameraPositionState = rememberCameraPositionState {
       position = CameraPosition.fromLatLngZoom(ArequipaLocation, 12f)
   }

   var mapType by remember { mutableStateOf(MapType.NORMAL) }
   var showMenu by remember { mutableStateOf(false) }

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

   // Definición de Polígonos
   val mallAventuraPolygon = listOf(
       LatLng(-16.432292, -71.509145),
       LatLng(-16.432757, -71.509626),
       LatLng(-16.433013, -71.509310),
       LatLng(-16.432566, -71.508853)
   )

   val parqueLambramaniPolygon = listOf(
       LatLng(-16.422704, -71.530830),
       LatLng(-16.422920, -71.531340),
       LatLng(-16.423264, -71.531110),
       LatLng(-16.423050, -71.530600)
   )

   val plazaDeArmasPolygon = listOf(
       LatLng(-16.398866, -71.536961),
       LatLng(-16.398744, -71.536529),
       LatLng(-16.399178, -71.536289),
       LatLng(-16.399299, -71.536721)
   )

   // Definición de Polilínea (Ejemplo: Ruta de Plaza de Armas a Mall Aventura)
   val routeToMall = listOf(
       LatLng(-16.398866, -71.536961), // Plaza de Armas
       LatLng(-16.422704, -71.530830), // Parque Lambramani
       LatLng(-16.432292, -71.509145)  // Mall Aventura
   )

   Box(modifier = Modifier.fillMaxSize()) {
       // Añadir GoogleMap al layout
       GoogleMap(
           modifier = Modifier.fillMaxSize(),
           cameraPositionState = cameraPositionState,
           properties = MapProperties(mapType = mapType)
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

           // Dibujar Polígonos
           Polygon(
               points = plazaDeArmasPolygon,
               strokeColor = Color.Red,
               fillColor = Color.Blue,
               strokeWidth = 5f
           )
           Polygon(
               points = parqueLambramaniPolygon,
               strokeColor = Color.Red,
               fillColor = Color.Blue,
               strokeWidth = 5f
           )
           Polygon(
               points = mallAventuraPolygon,
               strokeColor = Color.Red,
               fillColor = Color.Blue,
               strokeWidth = 5f
           )

           // Dibujar Polilínea (Ejemplo solicitado)
           Polyline(
               points = routeToMall,
               color = Color.Magenta,
               width = 10f
           )

           // --- FIGURA "CHEBRE": ESCRIBIR TECSUP ---
           
           val baseLat = -16.4240
           val baseLng = -71.5200
           val letterWidth = 0.0010
           val letterHeight = 0.0015
           val spacing = 0.0012

           // Letra T
           Polyline(points = listOf(LatLng(baseLat, baseLng), LatLng(baseLat, baseLng + letterWidth)), color = Color.Blue, width = 15f)
           Polyline(points = listOf(LatLng(baseLat, baseLng + letterWidth/2), LatLng(baseLat - letterHeight, baseLng + letterWidth/2)), color = Color.Blue, width = 15f)

           // Letra E
           val eLat = baseLat
           val eLng = baseLng + spacing
           Polyline(points = listOf(
               LatLng(eLat, eLng + letterWidth), LatLng(eLat, eLng), 
               LatLng(eLat - letterHeight/2, eLng), LatLng(eLat - letterHeight/2, eLng + letterWidth * 0.7),
               LatLng(eLat - letterHeight/2, eLng), LatLng(eLat - letterHeight, eLng), 
               LatLng(eLat - letterHeight, eLng + letterWidth)
           ), color = Color.Blue, width = 15f)

           // Letra C
           val cLat = baseLat
           val cLng = baseLng + spacing * 2
           Polyline(points = listOf(
               LatLng(cLat, cLng + letterWidth), LatLng(cLat, cLng), 
               LatLng(cLat - letterHeight, cLng), LatLng(cLat - letterHeight, cLng + letterWidth)
           ), color = Color.Blue, width = 15f)

           // Letra S
           val sLat = baseLat
           val sLng = baseLng + spacing * 3
           Polyline(points = listOf(
               LatLng(sLat, sLng + letterWidth), LatLng(sLat, sLng), 
               LatLng(sLat - letterHeight/2, sLng), LatLng(sLat - letterHeight/2, sLng + letterWidth),
               LatLng(sLat - letterHeight, sLng + letterWidth), LatLng(sLat - letterHeight, sLng)
           ), color = Color.Blue, width = 15f)

           // Letra U
           val uLat = baseLat
           val uLng = baseLng + spacing * 4
           Polyline(points = listOf(
               LatLng(uLat, uLng), LatLng(uLat - letterHeight, uLng), 
               LatLng(uLat - letterHeight, uLng + letterWidth), LatLng(uLat, uLng + letterWidth)
           ), color = Color.Blue, width = 15f)

           // Letra P
           val pLat = baseLat
           val pLng = baseLng + spacing * 5
           Polyline(points = listOf(
               LatLng(pLat - letterHeight, pLng), 
               LatLng(pLat, pLng), 
               LatLng(pLat, pLng + letterWidth), 
               LatLng(pLat - letterHeight/2, pLng + letterWidth),
               LatLng(pLat - letterHeight/2, pLng)
           ), color = Color.Blue, width = 15f)
       }

       // Menú Sandwich (Tipo Sandwich/Capas)
       Column(
           modifier = Modifier
               .padding(top = 64.dp, start = 16.dp) // Más abajo y a la izquierda
               .align(Alignment.TopStart),
           horizontalAlignment = Alignment.Start
       ) {
           Surface(
               shape = RoundedCornerShape(50),
               color = ComposeColor.White.copy(alpha = 0.9f),
               shadowElevation = 4.dp
           ) {
               IconButton(onClick = { showMenu = !showMenu }) {
                   Icon(
                       imageVector = Icons.Default.Layers,
                       contentDescription = "Menu capas",
                       tint = ComposeColor.DarkGray
                   )
               }
           }

           Spacer(modifier = Modifier.height(8.dp))

           AnimatedVisibility(
               visible = showMenu,
               enter = fadeIn(),
               exit = fadeOut()
           ) {
               Surface(
                   modifier = Modifier.fillMaxWidth(0.4f),
                   color = ComposeColor.White.copy(alpha = 0.9f),
                   shape = RoundedCornerShape(16.dp),
                   shadowElevation = 8.dp
               ) {
                   Column(
                       modifier = Modifier.padding(12.dp),
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       val types = listOf(
                           "Normal" to MapType.NORMAL,
                           "Satélite" to MapType.SATELLITE,
                           "Híbrido" to MapType.HYBRID,
                           "Terreno" to MapType.TERRAIN
                       )

                       types.forEach { (label, type) ->
                           OutlinedButton(
                               onClick = { 
                                   mapType = type
                                   showMenu = false // Cerrar al elegir
                               },
                               modifier = Modifier.fillMaxWidth(),
                               shape = RoundedCornerShape(8.dp),
                               border = if (mapType == type) 
                                   androidx.compose.foundation.BorderStroke(2.dp, ComposeColor.Blue) 
                               else 
                                   androidx.compose.foundation.BorderStroke(1.dp, ComposeColor.Gray)
                           ) {
                               Text(text = label, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                           }
                           Spacer(modifier = Modifier.height(4.dp))
                       }
                   }
               }
           }
       }
   }
}
