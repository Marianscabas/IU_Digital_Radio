package com.example.iudigitalradio.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iudigitalradio.data.model.Station
import com.example.iudigitalradio.data.repository.RadioRepository
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de negocio y del estado de la pantalla principal de la Radio.
 * Integra la obtención de emisoras en tiempo real desde Radio Browser API mediante el repositorio.
 */
class RadioViewModel : ViewModel() {

    private val repository = RadioRepository()

    // Estado reactivo para la foto de perfil del usuario
    var fotoUsuario by mutableStateOf<Bitmap?>(null)
        private set

    // Emisoras disponibles (inicializadas con el fallback y actualizadas desde la API)
    val stations = mutableStateListOf<Station>().apply {
        addAll(repository.defaultStations)
    }

    // Emisora seleccionada actualmente (por defecto la primera de la lista)
    var selectedStation by mutableStateOf(repository.defaultStations.first())
        private set

    // Estado play / pause
    var isPlaying by mutableStateOf(false)
        private set

    // Estado de mute
    var isMuted by mutableStateOf(false)
        private set

    // URL de la emisora seleccionada
    var selectedStationUrl by mutableStateOf(selectedStation.streamUrl)
        private set

    init {
        loadStationsFromApi()
    }

    /**
     * Carga de manera asíncrona las emisoras desde la API pública de Radio Browser.
     */
    private fun loadStationsFromApi() {
        viewModelScope.launch {
            val fetchedStations = repository.fetchStations()
            if (fetchedStations.isNotEmpty()) {
                stations.clear()
                stations.addAll(fetchedStations)
                // Asegurar que la emisora seleccionada sea válida dentro de la nueva lista
                if (!stations.any { it.streamUrl == selectedStation.streamUrl }) {
                    selectedStation = stations.first()
                    selectedStationUrl = stations.first().streamUrl
                }
            }
        }
    }

    /**
     * Actualiza la foto de perfil capturada desde la cámara.
     */
    fun updateFotoUsuario(bitmap: Bitmap?) {
        fotoUsuario = bitmap
    }

    /**
     * Alterna entre reproducir y pausar.
     */
    fun togglePlayPause() {
        isPlaying = !isPlaying
    }

    /**
     * Alterna el estado de silencio.
     */
    fun toggleMute() {
        isMuted = !isMuted
    }

    /**
     * Cambia la emisora activa.
     */
    fun selectStation(station: Station) {
        selectedStation = station
        selectedStationUrl = station.streamUrl
        isPlaying = true
    }
}
