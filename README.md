### busGps**

Este proyecto es una aplicación de escritorio en Java que simula un sistema de seguimiento GPS. Ofrece diversas funcionalidades como la generación de datos GPS, carga de archivos CSV, cálculo de velocidad media, detección de paradas y visualización de estadísticas. La interfaz gráfica está desarrollada con `Swing`.

---

### 📦 Clases y funcionalidades

---

#### **Main**

Clase de entrada principal del programa.

**Métodos:**

* `main(String[] args)`: Lanza la interfaz gráfica inicial (`VentanaGPS`).

#### **VentanaGPS**

Clase que implementa la interfaz gráfica de usuario para el sistema GPS, usando Java Swing.

**Métodos:**

* `VentanaGPS()`: Constructor que inicializa y configura la ventana principal (1080x720 px) con un fondo personalizado y botones centrados verticalmente con estilo moderno (fondo azul #6fc0f4, texto blanco, bordes redondeados). Los botones están organizados con separación vertical de 10px.
* `crearBoton(String texto, ActionListener action)`: Crea y devuelve un botón con una acción asociada, aplicando el estilo visual personalizado.
* `main(String[] args)`: Lanza la interfaz de usuario en el hilo de eventos de Swing.

**Funciones implementadas desde la GUI:**

* **Generar datos GPS**: Llama a `ReadWrite.archivar()` y `GenerarDatosGPS.generarDatos()` para simular y guardar datos GPS.
* **Cargar CSV**: Ejecuta `Menu.cargarCSV()` para cargar los datos desde un archivo.
* **Calcular velocidad media**: Llama a `ProcesarGPSdata.calcularVelocidadMedia(...)` con los datos cargados.
* **Detectar paradas**: Utiliza `ProcesarGPSdata.detectarParadas(...)` para identificar puntos de parada.
* **Mostrar estadísticas**: Muestra estadísticas procesadas mediante `Estadisticas.mostrarEstadisticas(...)`.

#### **ProcesarGPSdata**

Clase encargada de filtrar y analizar datos GPS.

**Métodos:**

* `procesarDatos(String busId, String horaInicio, String horaFin)`:
  Filtra datos GPS por ID de autobús y rango horario. Solo incluye datos válidos (coordenadas y velocidad coherente).
* `isValidGPSData(GPSData gpsData)`*(privado)*:
  Verifica que los datos tengan coordenadas válidas y velocidad no negativa.
* `calcularVelocidadMedia(ArrayList<GPSData> datosGPS)`:
  Calcula la velocidad media de un conjunto de datos GPS.
* `detectarParadas(ArrayList<GPSData> datos)`:
  Retorna un mapa con el número de paradas (velocidad = 0) por cada autobús.

#### **GenerarDatosGPS**

Clase encargada de simular y escribir datos GPS sintéticos en un archivo.

**Métodos:**

* `generarDatos()`:
  Genera datos aleatorios de posición y velocidad para varios autobuses y los escribe en un archivo CSV (`gps_data.csv`). Cada autobús genera datos por 60 minutos consecutivos.

#### **ReadWrite**

Encargada de la lectura y escritura de archivos CSV, así como del archivado de datos.

**Métodos:**

* `escribirDatosGPS(String busId, String timestamp, double latitude, double longitude, double speed)`:
  Añade una línea de datos al archivo `gps_data.csv`. Si es la primera escritura, agrega la cabecera del CSV.
* `leerDatosGPS()`:
  Lee el archivo `gps_data.csv` y devuelve las líneas como arrays de Strings. *(⚠️ Actualmente solo considera líneas con 3 campos, lo cual puede ser un error si el CSV tiene 5 columnas.)*
* `archivar()`:
  Mueve el archivo actual a una carpeta `archivados/` con un nombre único basado en fecha y hora. Luego crea un nuevo archivo `gps_data.csv` vacío con cabecera.
