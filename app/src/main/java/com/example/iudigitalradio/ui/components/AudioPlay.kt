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
) {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        try {
            ExoPlayer.Builder(context).build()
        } catch (_: Exception) {
            null
        }
    }

    LaunchedEffect(streamURL, exoPlayer) {
        val player = exoPlayer ?: return@LaunchedEffect
        try {
            if (streamURL.isNotEmpty()) {
                player.stop()
                player.clearMediaItems()
                val mediaItem = MediaItem.fromUri(streamURL)
                player.setMediaItem(mediaItem)
                player.prepare()
                if (isPlaying) {
                    player.play()
                }
            }
        } catch (_: Exception) {
        }
    }

    LaunchedEffect(isPlaying, exoPlayer) {
        val player = exoPlayer ?: return@LaunchedEffect
        try {
            if (isPlaying) {
                player.play()
            } else {
                player.pause()
            }
        } catch (_: Exception) {
        }
    }

    LaunchedEffect(isMuted, exoPlayer) {
        val player = exoPlayer ?: return@LaunchedEffect
        try {
            player.volume = if (isMuted) 0f else 1f
        } catch (_: Exception) {
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            try {
                exoPlayer?.stop()
                exoPlayer?.release()
            } catch (_: Exception) {
            }
        }
    }
}
