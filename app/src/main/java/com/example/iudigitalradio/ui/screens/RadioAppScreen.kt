package com.example.iudigitalradio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iudigitalradio.ui.components.PlayerSection
import com.example.iudigitalradio.ui.components.ProfileSection
import com.example.iudigitalradio.ui.components.StationList
import com.example.iudigitalradio.ui.theme.IUDigitalRadioTheme
import com.example.iudigitalradio.ui.viewmodel.RadioViewModel

/**
 * Pantalla principal orquestadora de la aplicación de radio.
 * Conecta el estado del ViewModel con los componentes visuales puros.
 */
@Composable
fun RadioAppScreen(
    modifier: Modifier = Modifier,
    viewModel: RadioViewModel = remember { RadioViewModel() }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D14))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp)) // padding top de la sección de perfil
        
        ProfileSection(
            fotoUsuario = viewModel.fotoUsuario,
            onFotoCaptured = { bitmap -> viewModel.updateFotoUsuario(bitmap) }
        )

        PlayerSection()

        StationList(stations = viewModel.stations)
    }
}

@Preview(showBackground = true)
@Composable
fun RadioAppScreenPreview() {
    IUDigitalRadioTheme {
        RadioAppScreen()
    }
}
