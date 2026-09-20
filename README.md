# IU Digital Radio — Evidencia de Aprendizaje 3

Aplicación móvil nativa en Android (Kotlin + Jetpack Compose) que integra maquetación declarativa, gestión de estado dinámico y acceso a hardware (cámara y vibración), cumpliendo los requerimientos funcionales RF-01 a RF-07 de la guía de la evidencia.

Modalidad de trabajo: **Software House (Equipo Célula, 3 integrantes)**. Todos cubren de forma transversal el ciclo de vida del software, repartiéndose los módulos de la aplicación.

## Estado actual del proyecto

Ya implementado (commit `Interfaz + camara`):

- ✅ RF-01 — Interfaz base en Jetpack Compose (`Column`, `Row`, `Card`, `LazyColumn`) en [MainActivity.kt](app/src/main/java/com/example/iudigitalradio/MainActivity.kt).
- ✅ RF-02 — Captura de foto de perfil con `ActivityResultContracts.TakePicturePreview`.
- ✅ RF-03 (parcial) — Permiso de `CAMERA` solicitado en tiempo de ejecución; `CAMERA` y `VIBRATE` declarados en el [AndroidManifest.xml](app/src/main/AndroidManifest.xml).
- ✅ Estructura visual de las 3 secciones: Perfil, Reproductor central, Catálogo de emisoras.

## Arquitectura del Proyecto (Patrón MVVM)

El proyecto ha sido refactorizado e institucionalizado bajo el patrón arquitectónico oficial recomendado por Google: **MVVM (Model-View-ViewModel)**. Esto separa completamente el diseño visual de la lógica del negocio, maximizando la modularidad y permitiendo que cada miembro de la célula trabaje de forma paralela y limpia:

- **`data/model/Station.kt`**: Modelo conceptual puro que define los atributos de una emisora de radio (`name`, `genreAndFrequency`).
- **`ui/viewmodel/RadioViewModel.kt`**: Encargado de almacenar y preservar los estados reactivos mutables de la pantalla (como la foto capturada por la cámara) e inyectar los flujos de datos. Esto previene pérdidas de datos ante rotaciones de pantalla o cambios de configuración.
- **`ui/components/`**: Módulos visuales puros reutilizables e independientes:
  - `ProfileSection.kt`: Vista aislada de la tarjeta de bienvenida y llamada segura a la cámara nativa.
  - `PlayerSection.kt`: Panel de control de reproducción de medios.
  - `StationList.kt`: Listado eficiente (`LazyColumn`) para pintar las emisoras de radio.
- **`ui/screens/RadioAppScreen.kt`**: Orquestador principal que enlaza las propiedades reactivas del `RadioViewModel` con cada componente visual.
- **`MainActivity.kt`**: Actividad simplificada al máximo que actúa exclusivamente como el punto de entrada y contenedor de inicialización del Tema de Material 3 (`IUDigitalRadioTheme`).

Pendiente (lo que falta repartir entre los 3 integrantes):

- ⬜ RF-04 — Estado dinámico real del reproductor (`isPlaying`, `selectedStation`) con `mutableStateOf` / `rememberSaveable`.
- ⬜ RF-05 — Vibración (`Vibrator` / `VibratorManager`) al presionar Play, Pause o Mute.
- ⬜ RF-06 (completar) — Que al tocar una emisora de la `LazyColumn` se actualice la emisora activa mostrada en el reproductor.
- ⬜ RF-07 — Reproducción de audio (real con Media3 ExoPlayer o simulada con estados visuales).
- ⬜ Paso 5 — Compilación y generación del `.apk`, pruebas en dispositivo/emulador.
- ⬜ Documento técnico (PDF) + video de demostración (2-4 min) para la entrega.

## Reparto de actividades

### 🧑‍💻 Persona 1 — Estado, Lógica e Interactividad (RF-04 y RF-06)

Responsable de que la app deje de ser "estática" y reaccione a las acciones del usuario.

- [ ] Crear los estados `isPlaying: Boolean` y `selectedStation` (nombre + género) con `mutableStateOf`/`rememberSaveable` en `RadioAppScreen`.
- [ ] Conectar los botones Play/Pause de `PlayerSection` para alternar `isPlaying` (cambiar ícono `PlayArrow` ⇄ `Pause` según el estado).
- [ ] Conectar el botón Mute para alternar un estado `isMuted` (cambiar ícono `VolumeUp` ⇄ `VolumeOff`).
- [ ] Hacer clickeable cada `StationItem` en `StationList` para que, al presionarlo, actualice `selectedStation` y el `PlayerSection` muestre esa emisora (nombre + género).
- [ ] Verificar que el estado sobreviva a la rotación de pantalla (`rememberSaveable`).
- [ ] Tomar capturas de pantalla: reproductor en Play vs Pause, y cambio de emisora seleccionada.

### 🧑‍💻 Persona 2 — Hardware: Vibración y Permisos (RF-05 y refuerzo de RF-03)

Responsable de la retroalimentación háptica y de que la gestión de permisos quede completa.

- [ ] Obtener el servicio de vibración (`VibratorManager` en API 31+ / `Vibrator` en versiones anteriores) mediante `getSystemService`.
- [ ] Implementar una función `vibrarCorto()` que dispare una pulsación háptica corta (`VibrationEffect.createOneShot`).
- [ ] Llamar `vibrarCorto()` dentro de los `onClick` de Play, Pause y Mute (coordinar con Persona 1, que ya deja esos `onClick` conectados al estado).
- [ ] Revisar que el flujo de permiso de `CAMERA` maneje también el caso en que el usuario lo **deniega** (mostrar mensaje o estado alternativo, no solo el `if (concedido)`).
- [ ] Confirmar que `VIBRATE` (permiso normal, no peligroso) esté correctamente declarado — ya está en el manifest, solo validar que no se necesita solicitud en tiempo de ejecución.
- [ ] Tomar capturas de pantalla: cuadro de diálogo de permisos y evidencia de la vibración (se puede describir en el documento, ya que no se ve en captura).

### 🧑‍💻 Persona 3 — Audio/Streaming y Empaquetado Final (RF-07 y Paso 5)

Responsable de que la app "suene" (real o simulado) y de dejar el proyecto listo para entregar.

- [ ] Agregar la dependencia de Media3 ExoPlayer en [app/build.gradle.kts](app/build.gradle.kts) (`androidx.media3:media3-exoplayer` y `androidx.media3:media3-ui` o `media3-common`).
- [ ] Integrar la reproducción de audio: real (URL de streaming o archivo local) sincronizada con el estado `isPlaying` de Persona 1, o simulada (estado visual tipo ecualizador animado) si no hay URLs de streaming disponibles — documentar cuál se eligió.
- [ ] Manejar el ciclo de vida del `ExoPlayer` (liberar recursos en `onDispose`/`onStop` para no dejar el audio sonando en segundo plano indebidamente).
- [ ] Compilar el APK de depuración desde Android Studio (Build > Build APK(s)) y extraerlo de `app/build/outputs/apk/debug/`.
- [ ] Probar la app completa en un dispositivo físico o emulador (checklist funcional de los 3 módulos).
- [ ] Grabar el video de demostración (2-4 min) cubriendo: inicio de la app, permisos de cámara, foto de perfil, botones Play/Pause/Mute con vibración, y cambio de emisora. Subirlo a Google Drive.
- [ ] Armar el documento técnico en PDF (portada, arquitectura, capturas, enlace al repositorio, enlace al video) y coordinar con Persona 1 y 2 para reunir sus capturas.

## Entregables finales (responsabilidad compartida)

1. **Documento técnico (PDF)**: portada con integrantes y modalidad, arquitectura de componentes, capturas de pantalla de cada módulo, enlace al repositorio Git.
2. **Video de demostración (2-4 min)** subido a Google Drive, enlazado en el documento.
3. **Repositorio Git** (este repositorio) con el código fuente completo y funcional.

## Requerimientos funcionales (referencia rápida)

| ID | Requerimiento | Responsable |
|----|---------------|-------------|
| RF-01 | Maquetación UI Declarativa | ✅ Hecho |
| RF-02 | Perfil con Captura de Cámara | ✅ Hecho |
| RF-03 | Gestión de Permisos en Tiempo de Ejecución | ✅ Hecho / Persona 2 (refuerzo) |
| RF-04 | Reproductor Interactivo y Estado Dinámico | Persona 1 |
| RF-05 | Retroalimentación Háptica (Vibración) | Persona 2 |
| RF-06 | Lista Dinámica de Emisoras | Persona 1 |
| RF-07 | Streaming / Audio Player | Persona 3 |
