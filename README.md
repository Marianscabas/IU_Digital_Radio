# IU Digital Radio — Evidencia de Aprendizaje 3 (Taller Práctico)

Aplicación móvil nativa desarrollada para Android en **Kotlin** y **Jetpack Compose**, cumpliendo con la totalidad de los requerimientos funcionales y técnicos de la **Evidencia de Aprendizaje 3** de la **IU Digital**.

---

## 📋 Resultado de Aprendizaje
Desarrollar una aplicación móvil nativa para Android integrando maquetación declarativa en Jetpack Compose, gestión de estado dinámico y acceso a hardware del dispositivo (cámara, vibración y streaming de audio) con su correspondiente esquema de permisos, para solucionar un requerimiento técnico funcional y empaquetar el producto final en un ejecutable instalable (`.apk`).

---

## ✅ Cumplimiento de Requerimientos Funcionales (RF-01 al RF-07)

| ID | Requerimiento Funcional | Estado | Implementación en Código |
|----|-------------------------|--------|--------------------------|
| **RF-01** | Maquetación UI Declarativa | ✅ **Completado** | [`RadioAppScreen.kt`](app/src/main/java/com/example/iudigitalradio/ui/screens/RadioAppScreen.kt) usando Compose, Material 3, `Column`, `Row`, `Card` y `LazyColumn`. |
| **RF-02** | Captura de foto de perfil con cámara | ✅ **Completado** | [`ProfileSection.kt`](app/src/main/java/com/example/iudigitalradio/ui/components/ProfileSection.kt) mediante `ActivityResultContracts.TakePicturePreview`. |
| **RF-03** | Gestión de permisos en tiempo de ejecución | ✅ **Completado** | Solicitud dinámica de `CAMERA` y verificación de `VIBRATE` e `INTERNET` en el [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml). |
| **RF-04** | Reproductor interactivo y estado dinámico | ✅ **Completado** | [`PlayerSection.kt`](app/src/main/java/com/example/iudigitalradio/ui/components/PlayerSection.kt) vinculado a [`RadioViewModel.kt`](app/src/main/java/com/example/iudigitalradio/ui/viewmodel/RadioViewModel.kt) con estados reactivos (`isPlaying`, `isMuted`). |
| **RF-05** | Retroalimentación háptica (Vibración) | ✅ **Completado** | [`MainActivity.kt`](app/src/main/MainActivity.kt) disparando pulsaciones cortas con `Vibrator` / `VibratorManager` en acciones táctiles clave. |
| **RF-06** | Lista dinámica e interactiva de emisoras | ✅ **Completado** | [`StationList.kt`](app/src/main/java/com/example/iudigitalradio/ui/components/StationList.kt) con `LazyColumn` que actualiza la emisora activa en tiempo real. |
| **RF-07** | Streaming de audio en vivo / Reproductor | ✅ **Completado** | [`AudioPlay.kt`](app/src/main/java/com/example/iudigitalradio/ui/components/AudioPlay.kt) utilizando **Jetpack Media3 ExoPlayer** conectado a **Radio Browser API** con fallback local. |

---

## 🏛️ Arquitectura del Proyecto (Patrón MVVM)

El proyecto sigue estrictamente el patrón arquitectónico oficial recomendado por Google (**MVVM**), separando las responsabilidades de manera modular:

```text
com.example.iudigitalradio/
│
├── data/
│   ├── model/
│   │   └── Station.kt                  # Modelo de datos puro de la emisora
│   └── repository/
│       └── RadioRepository.kt          # Consumo de Radio Browser API + Fallback estático
│
├── ui/
│   ├── components/
│   │   ├── AudioPlay.kt                # Reproductor multimedia con Media3 ExoPlayer
│   │   ├── PlayerSection.kt            # Panel visual del reproductor (Play, Pause, Mute)
│   │   ├── ProfileSection.kt           # Sección de bienvenida y captura de cámara
│   │   └── StationList.kt              # Listado optimizado LazyColumn de emisoras
│   ├── screens/
│   │   └── RadioAppScreen.kt           # Pantalla orquestadora principal
│   └── theme/                          # Colores, Tipografía y Tema Material 3
│
└── MainActivity.kt                     # Punto de entrada y controlador seguro de Vibración
```

---

## ⚙️ Detalles Técnicos de Implementación

### 1. Consumo de API y Resiliencia (`RadioRepository`)
La aplicación consume en tiempo real la **Radio Browser API** (`https://de1.api.radio-browser.info/json/stations/topclick/15`) utilizando corrutinas de Kotlin (`Dispatchers.IO`) e `HttpURLConnection`. 
* **Fallback Inteligente:** Si el dispositivo no tiene conexión a internet o la API no responde, el repositorio carga automáticamente un listado estático preconfigurado de emisoras, garantizando que la aplicación jamás falle durante demostraciones o evaluaciones académicas.

### 2. Gestión de Hardware y Seguridad (`Camera` & `Vibration`)
* **Cámara:** Se utiliza `rememberLauncherForActivityResult` con `TakePicturePreview` combinado con verificación de permisos en tiempo de ejecución (`ActivityResultContracts.RequestPermission`).
* **Vibración:** Implementada de forma segura en `MainActivity` utilizando `VibratorManager` (API 31+) y `Vibrator` con *safe-casting* (`as?`) y bloques `try-catch`, evitando cualquier fallo (*NullPointerException*) en tablets o dispositivos sin motor háptico.

### 3. Reproducción de Audio en Vivo (`Media3 ExoPlayer`)
* Integración robusta de `androidx.media3:media3-exoplayer`.
* Control sincronizado del ciclo de vida mediante `LaunchedEffect` y `DisposableEffect` para pausar, mutear y liberar recursos de memoria eficientemente.

---

## 📦 Compilación y Empaquetado APK

La aplicación se encuentra compilada y lista para su instalación:
* **Ruta del ejecutable APK:** `app/build/outputs/apk/debug/app-debug.apk`
* **Compatibilidad:** Android 7.0 (API 24) en adelante hasta Android 15/16 (API 37).

---
*Desarrollado para la Evidencia de Aprendizaje 3 – IU Digital Radio.*
