package com.example.iudigitalradio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.iudigitalradio.ui.screens.RadioAppScreen
import com.example.iudigitalradio.ui.theme.IUDigitalRadioTheme

/**
 * Actividad principal que sirve como punto de entrada de la aplicación.
 */
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
