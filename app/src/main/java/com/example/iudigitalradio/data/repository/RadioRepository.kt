package com.example.iudigitalradio.data.repository

import android.util.Log
import com.example.iudigitalradio.data.model.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class RadioRepository {

    val defaultStations = listOf(
        Station("Electro Pulse FM", "Electronic · 98.5 MHz", "https://uk7.internet-radio.com/proxy/movedahouse?mp=/stream;"),
        Station("Jazz Lounge 24", "Jazz · 101.3 MHz", "https://uk3.internet-radio.com/proxy/majesticjukebox?mp=/stream"),
        Station("Deep House Radio", "House · 104.7 MHz", "https://uk2.internet-radio.com/proxy/danceuk?mp=/stream;"),
        Station("Classical WQXR", "Classical · 96.3 MHz", "http://philae.shoutca.st:8204/stream/1/"),
        Station("Urban Beats HQ", "Hip-Hop · 92.1 MHz", "https://us2.internet-radio.com/proxy/riddim1radio?mp=/stream;")
    )

    suspend fun fetchStations(): List<Station> = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://de1.api.radio-browser.info/json/stations/topclick/15")
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 6000
                readTimeout = 6000
                setRequestProperty("User-Agent", "IUDigitalRadioApp/1.0")
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val responseString = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(responseString)
                val fetchedList = mutableListOf<Station>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val name = obj.optString("name", "Emisora Desconocida").trim()
                    val streamUrl = obj.optString("url_resolved", obj.optString("url", "")).trim()
                    val tags = obj.optString("tags", "Radio Online").trim()
                    val country = obj.optString("country", "").trim()

                    if (name.isNotEmpty() && streamUrl.isNotEmpty()) {
                        val genreText = if (country.isNotEmpty()) "$tags · $country" else tags
                        fetchedList.add(Station(name = name, genreAndFrequency = genreText, streamUrl = streamUrl))
                    }
                }

                if (fetchedList.isNotEmpty()) {
                    return@withContext fetchedList
                }
            }
        } catch (e: Exception) {
            Log.e("RadioRepository", "Error", e)
        }
        return@withContext defaultStations
    }
}
