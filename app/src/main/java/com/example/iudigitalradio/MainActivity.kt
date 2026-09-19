package com.example.iudigitalradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iudigitalradio.ui.theme.IUDigitalRadioTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Radio
import android.graphics.Bitmap
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IUDigitalRadioTheme {
                RadioAppScreen()
            }
        }
    }
}

@Composable
fun RadioAppScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D14))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp)) // padding top de la sección de perfil
        ProfileSection()

        PlayerSection()

        StationList()
    }
}

@Composable
fun ProfileSection(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var fotoUsuario by remember { mutableStateOf<Bitmap?>(null) }

    // Lanzador que abre la cámara y recibe la foto capturada
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        fotoUsuario = bitmap
    }

    // Lanzador que pide el permiso de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            cameraLauncher.launch(null)
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.BottomEnd) {
                // Círculo de foto: muestra la foto si existe, o el color placeholder si no
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF7C4DFF))
                ) {
                    fotoUsuario?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Botón de cámara
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A2E))
                        .clickable {
                            val permisoConcedido = ContextCompat.checkSelfPermission(
                                context, Manifest.permission.CAMERA
                            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

                            if (permisoConcedido) {
                                cameraLauncher.launch(null)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Tomar foto",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = "Buenos días", color = Color(0xFF9C8CFF), fontSize = 14.sp)
                Text(
                    text = "Alejandro Torres",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF7C4DFF))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(text = "EN VIVO", color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun PlayerSection(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A2E))
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "REPRODUCIENDO AHORA",
                    color = Color(0xFF9C8CFF),
                    fontSize = 12.sp
                )
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = "Sonando",
                    tint = Color(0xFF9C8CFF),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Jazz Lounge 24",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Jazz · 101.3 MHz",
                color = Color(0xFF9C8CFF),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Mute
                IconButton(
                    onClick = { /* luego conectamos esto */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Mute",
                        tint = Color.White
                    )
                }

                // Botón Play/Pause (más grande)
                Box(contentAlignment = Alignment.Center) {
                    // Círculo decorativo (el "disco" de fondo)
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7C4DFF).copy(alpha = 0.15f))
                    )

                    // Botón de Play (el mismo de antes, sin cambios)
                    IconButton(
                        onClick = { /* luego conectamos esto */ },
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7C4DFF))
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White
                        )
                    }
                }

                // Botón Favorito (corazón)
                IconButton(
                    onClick = { /* luego conectamos esto */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
@Composable
fun StationList(modifier: Modifier = Modifier) {
    val emisoras = listOf(
        "Electro Pulse FM" to "Electronic · 98.5 MHz",
        "Jazz Lounge 24" to "Jazz · 101.3 MHz",
        "Deep House Radio" to "House · 104.7 MHz",
        "Classical WQXR" to "Classical · 96.3 MHz",
        "Urban Beats HQ" to "Hip-Hop · 92.1 MHz"
    )

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
            items(emisoras) { emisora ->
                StationItem(nombre = emisora.first, genero = emisora.second)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun RadioAppScreenPreview() {
    IUDigitalRadioTheme {
        RadioAppScreen()
    }
}