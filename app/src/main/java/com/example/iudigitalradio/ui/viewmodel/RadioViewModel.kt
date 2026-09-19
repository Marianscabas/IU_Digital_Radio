package com.example.iudigitalradio.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.iudigitalradio.data.model.Station

/**
 * ViewModel encargado de la lógica de negocio y del estado de la pantalla principal de la Radio.
 */
class RadioViewModel : ViewModel() {

    // Estado reactivo para la foto de perfil del usuario
    var fotoUsuario by mutableStateOf<Bitmap?>(null)
        private set

    // Lista inmutable de emisoras de la radio utilizando el modelo de datos formal
    val stations = listOf(
        Station("Electro Pulse FM", "Electronic · 98.5 MHz"),
        Station("Jazz Lounge 24", "Jazz · 101.3 MHz"),
        Station("Deep House Radio", "House · 104.7 MHz"),
        Station("Classical WQXR", "Classical · 96.3 MHz"),
        Station("Urban Beats HQ", "Hip-Hop · 92.1 MHz")
    )

    /**
     * Actualiza la foto de perfil capturada desde la cámara.
     */
    fun updateFotoUsuario(bitmap: Bitmap?) {
        fotoUsuario = bitmap
    }
}
