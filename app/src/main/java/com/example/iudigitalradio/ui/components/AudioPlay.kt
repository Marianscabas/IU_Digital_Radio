package com.example.iudigitalradio.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

//componente para controlar la reproduccion del audio
@Composable
fun AudioPlayer(
    streamURL: String,
    isPlaying: Boolean,
    isMuted: Boolean
){
    //obtiene el contexto actual de la aplicacion
    val context = LocalContext.current

    //crea el resproductor exoplayer
    val exoPlayer = remember(context){
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(streamURL)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    //se ejecuta cuando cambia la URL del audio
    LaunchedEffect(streamURL){
        //crea un nuevo elemento multimedia
        val mediaItem = MediaItem.fromUri(streamURL)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        if (isPlaying) exoPlayer.play()
    }
    //se ejecuta cuando cambia el estado de la reproduccion
    LaunchedEffect(isPlaying){
        if (isPlaying) exoPlayer.play()
        else exoPlayer.pause()
    }
    //se ejecuta cuando cambia el estado a muted
    LaunchedEffect(isMuted){
        exoPlayer.volume = if (isMuted) 0f
        else 1f
    }
    //se utiliza para dejar de usar los recuros del reproductor cuando se dejo de utilizar
    DisposableEffect(Unit){
        onDispose{
            exoPlayer.stop()
            exoPlayer.release()
        }
    }
}