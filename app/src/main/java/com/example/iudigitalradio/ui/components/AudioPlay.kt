package com.example.iudigitalradio.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun AudioPlayer(
    streamURL: String,
    isPlaying: Boolean,
    isMuted: Boolean
){
    val context = LocalContext.current

    val exoPlayer = remember(context){
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(streamURL)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    LaunchedEffect(streamURL){
        val mediaItem = MediaItem.fromUri(streamURL)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        if (isPlaying) exoPlayer.play()
    }

    LaunchedEffect(isPlaying){
        if (isPlaying) exoPlayer.play()
        else exoPlayer.pause()
    }

    LaunchedEffect(isMuted){
        exoPlayer.volume = if (isMuted) 0f
        else 1f
    }

    DisposableEffect(Unit){
        onDispose{
            exoPlayer.stop()
            exoPlayer.release()
        }
    }
}