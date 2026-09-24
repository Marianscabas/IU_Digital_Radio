package com.example.iudigitalradio.data.model

/**
 * Modelo de datos que representa una emisora de radio.
 */
data class Station(
    val name: String,
    val genreAndFrequency: String,
    val streamUrl: String
)
