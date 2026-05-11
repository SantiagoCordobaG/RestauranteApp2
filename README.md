# RestauranteApp 🍽️ & Sistema de Exámenes 📝

Aplicación Android desarrollada en **Kotlin** que combina la gestión de pedidos de un restaurante con un módulo de registro y seguimiento de exámenes. El proyecto utiliza almacenamiento local con **Room Database**, pantallas XML tradicionales y corrutinas para ejecutar operaciones de base de datos sin bloquear la interfaz.

---

## 🚀 Funcionalidades principales

### 1. Gestión de pedidos y detalles

- Registro de pedidos con:
    - Fecha del pedido.
    - Número de mesa.
    - Estado del pedido.

- Visualización de pedidos existentes.
- Acceso al detalle de cada pedido.
- Registro de platos dentro de un pedido.
- Cálculo de valores asociados al detalle del pedido.
- Manejo de observaciones por cada plato solicitado.

> Nota: El módulo de pedidos conserva la entidad `Plato`, ya que esta sigue siendo necesaria para `DetallePedidoActivity`, aun asi esto se debe corregir pq esto es una veterinaria no un restaurante.

---

### 2. Módulo de exámenes

El módulo de exámenes fue agregado siguiendo la estructura de la tabla `examen`.

Permite:

- Registrar exámenes.
- Listar exámenes existentes.
- Editar exámenes registrados.
- Actualizar información de un examen.
- Volver al inicio desde la pantalla de exámenes.
- Acceder a la gestión de parámetros de cada examen.

Campos gestionados:

- Descripción del examen.
- Valor del examen.
- Tipo de examen.
- Estado del examen.

Tipos disponibles:

- Laboratorio
- Imagenología
- Clínico
- Otro

Estados disponibles:

- Activo
- Inactivo

---

### 3. Parámetros de examen

Se agregó una pantalla independiente para gestionar los parámetros asociados a cada examen, siguiendo la estructura de la tabla `examen_parametros`.

Permite:

- Asociar parámetros a un examen específico.
- Guardar el ID del examen seleccionado.
- Registrar el ID del parámetro.
- Registrar el consecutivo.
- Listar los parámetros asociados al examen.
- Volver a la pantalla de exámenes.
- Volver al inicio.

El flujo funciona así:

1. El usuario registra o visualiza un examen.
2. En la lista de exámenes aparece el botón **Parámetros**.
3. Al presionar ese botón, se abre `ExamenParametrosActivity`.
4. Se envía el `exa_id` y la descripción del examen mediante `Intent`.
5. Los parámetros se guardan relacionados con ese examen mediante `ep_exa_id`.

---

## 🗃️ Tablas de referencia

### Tabla `examen`

```sql
CREATE TABLE `examen` (
  `exa_id` int(11) NOT NULL,
  `exa_descripcion` varchar(100) NOT NULL,
  `exa_valor` int(11) NOT NULL,
  `exa_tipo` varchar(30) NOT NULL,
  `exa_estado` varchar(15) NOT NULL
);

CREATE TABLE `examen_parametros` (
  `ep_id` int(11) NOT NULL,
  `ep_exa_id` int(11) NOT NULL,
  `ep_para_id` int(11) NOT NULL,
  `ep_consecutivo` int(11) NOT NULL
);

Entidades Room implementadas

@Entity(tableName = "examen")
data class Examen(
    @PrimaryKey(autoGenerate = true)
    val exa_id: Int = 0,
    val exa_descripcion: String,
    val exa_valor: Int,
    val exa_tipo: String,
    val exa_estado: String
)

@Entity(tableName = "examen_parametros")
data class ExamenParametro(
    @PrimaryKey(autoGenerate = true)
    val ep_id: Int = 0,
    val ep_exa_id: Int,
    val ep_para_id: Int,
    val ep_consecutivo: Int
)

---

## 🎨 Mejoras de interfaz UI/UX

Se realizaron mejoras visuales en las pantallas principales del proyecto, manteniendo el uso de **layouts XML tradicionales** y evitando librerías externas para conservar una estructura simple y fácil de adaptar.

Estas mejoras se enfocaron en hacer que la aplicación tenga una apariencia más moderna, limpia y organizada, usando colores suaves, tarjetas redondeadas, botones personalizados y una mejor jerarquía visual.

---

### Pantalla principal `activity_main.xml`

Se rediseñó la pantalla principal de la aplicación para mejorar la presentación inicial del sistema.

Cambios realizados:

- Se agregó un fondo degradado en tonos turquesa y verde agua.
- Se incorporó una tarjeta central blanca con bordes redondeados.
- Se añadió un icono vectorial personalizado para representar el módulo de restaurante.
- Se mejoró la presentación del título y descripción inicial.
- Se personalizaron los botones principales:
    - **Exámenes**
    - **Pedido**
- Se mantuvieron IDs claros para facilitar el uso desde Kotlin, como:
    - `btnExamenes`
    - `btnPedido`
    - `imgRestaurante`
    - `cardInicio`

El diseño se realizó usando componentes XML estándar como:

- `ScrollView`
- `LinearLayout`
- `TextView`
- `ImageView`
- `Button`

---

### Pantalla de registro de exámenes `activity_examen.xml`

También se mejoró visualmente la pantalla de registro de exámenes, conservando los IDs originales para no afectar la lógica existente del proyecto.

Cambios realizados:

- Se agregó un fondo degradado verde/turquesa.
- El formulario fue organizado dentro de una tarjeta blanca redondeada.
- Se mejoró la separación visual entre secciones.
- Se agregaron etiquetas descriptivas para cada campo.
- Se estilizaron los campos `EditText` con fondo claro y bordes suaves.
- Se estilizaron los `Spinner` de tipo y estado del examen.
- Se creó un botón principal para **Guardar examen**.
- Se creó un botón secundario diferenciado para **Volver al inicio**.
- Se agregó una sección visual para los exámenes registrados.
- Se agregaron comentarios dentro del XML para facilitar la lectura y modificación del diseño.

IDs principales conservados:

- `linearExamenes`
- `etDescripcionExamen`
- `etValorExamen`
- `spTipoExamen`
- `spEstadoExamen`
- `btnGuardarExamen`
- `btnVolverInicio`

---

### Recursos visuales agregados

Para mantener una interfaz consistente y reutilizable, se agregaron varios archivos XML dentro de `res/drawable`.

Archivos agregados o utilizados:

- `bg_fondo_restaurante.xml`
- `bg_fondo_examen.xml`
- `bg_tarjeta_blanca.xml`
- `bg_input_claro.xml`
- `bg_boton_verde_oscuro.xml`
- `bg_boton_rosado.xml`
- `bg_volver_a_inicio.xml`
- `bg_circulo_turquesa.xml`
- `ic_restaurante.xml`

Estos archivos permiten reutilizar estilos para fondos, tarjetas, campos de texto, botones e iconos sin necesidad de agregar dependencias externas.

---

### Colores agregados en `colors.xml`

Se agregaron colores reutilizables para mantener coherencia visual en toda la aplicación.

Colores principales:

- `turquesa_fondo`
- `turquesa_claro`
- `verde_agua`
- `verde_oscuro`
- `boton_verde_oscuro`
- `boton_rosado`
- `blanco`
- `blanco_suave`
- `gris_oscuro`
- `gris_medio`
- `input_claro`
- `borde_input`

Estos colores se usan para fondos, tarjetas, textos, botones y campos de entrada.

---

### Enfoque del diseño

El rediseño mantiene una estructura sencilla, adecuada para un proyecto académico:

- No se usó Jetpack Compose.
- No se agregaron librerías externas.
- No se usó `CardView`.
- Las tarjetas se hicieron con `LinearLayout` y fondos XML redondeados.
- Los botones personalizados se hicieron mediante archivos `drawable`.
- La interfaz sigue siendo fácil de copiar, modificar y adaptar.

Con estas mejoras, la aplicación conserva su funcionalidad original, pero presenta una interfaz más moderna, amigable y ordenada para el usuario.