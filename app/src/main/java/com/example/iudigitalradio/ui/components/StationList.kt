package com.example.iudigitalradio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iudigitalradio.data.model.Station

/**
 * Listado de emisoras disponibles utilizando el componente optimizado LazyColumn.
 */
@Composable
fun StationList(
    stations: List<Station>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Emisoras",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ver todo",
                color = Color(0xFF9C8CFF),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(stations) { station ->
                StationItem(nombre = station.name, genero = station.genreAndFrequency)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Tarjeta individual para mostrar cada elemento de emisora.
 */
@Composable
fun StationItem(nombre: String, genero: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono pequeño de radio
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A40)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Radio,
                    contentDescription = "Emisora",
                    tint = Color(0xFF9C8CFF),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = genero, color = Color(0xFF9C8CFF), fontSize = 13.sp)
            }
        }
    }
}
