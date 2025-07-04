

## Descripción General

**AppChat** es una aplicación de escritorio de mensajería instantánea desarrollada en Java. Permite a los usuarios registrados comunicarse de manera segura y eficiente con sus contactos, ya sea en conversaciones individuales o en chats grupales.

El proyecto sigue una arquitectura similar al patrón **Modelo-Vista-Controlador (MVC)** para separar las responsabilidades:

* **Modelo (`dominio`)**: Contiene la lógica de negocio y las entidades principales como `Usuario`, `Contacto`, `Grupo` y `Mensaje`.
* **Vista (`vista`)**: Implementa la interfaz de usuario utilizando la biblioteca **Java Swing**. Incluye ventanas para el login, registro, la pantalla principal, gestión de contactos y grupos, entre otras.
* **Controlador (`controlador`)**: Actúa como intermediario entre la vista y el modelo. Gestiona las acciones del usuario y actualiza los datos correspondientes.
* **Persistencia (`persistencia`)**: Se encarga de la comunicación con la base de datos, utilizando el patrón **DAO (Data Access Object)** para abstraer el almacenamiento de los datos.

---

## Características Principales

### Autenticación y Perfil de Usuario
* **Registro de Usuarios**: Nuevos usuarios pueden registrarse proporcionando datos como nombre, teléfono, email y contraseña. El sistema valida que los datos sean correctos y que el teléfono no esté previamente registrado.
* **Login de Usuario**: Los usuarios registrados pueden acceder a la aplicación con su teléfono y contraseña.
* **Perfil Personalizable**: Cada usuario tiene un perfil que incluye una foto, un mensaje de saludo y la posibilidad de ver sus datos personales. El usuario puede cambiar su foto de perfil en cualquier momento desde la ventana principal.

### Gestión de Contactos y Grupos
* **Añadir Contactos**: Un usuario puede añadir a otros usuarios registrados como contactos, buscándolos por su número de teléfono. No se pueden añadir contactos con un teléfono que no exista en el sistema o contactos que ya estén en la lista.
* **Creación de Grupos**: Los usuarios pueden crear grupos, asignarles un nombre, una imagen y seleccionar a los miembros de entre su lista de contactos. Un grupo debe tener al menos dos miembros.
* **Modificación de Grupos**: Es posible modificar los miembros de un grupo existente, añadiendo o eliminando contactos.
* **Visualización Unificada**: La ventana principal muestra una lista combinada de contactos individuales y grupos para un acceso rápido a todas las conversaciones.

### Sistema de Mensajería
* **Chat Individual y Grupal**: La interfaz principal permite seleccionar un contacto o un grupo para iniciar una conversación.
* **Envío de Mensajes y Emoticonos**: Además de texto, los usuarios pueden enviar emoticonos en sus conversaciones.
* **Historial de Conversaciones**: Los mensajes se guardan y se asocian a cada contacto o grupo, manteniendo un historial de la conversación.

### Funcionalidades Premium
* **Suscripción Premium**: Los usuarios pueden optar por una suscripción de pago para desbloquear funcionalidades avanzadas.
* **Sistema de Descuentos**: El precio de la suscripción puede reducirse gracias a un sistema de descuentos dinámico. Se aplican descuentos si el usuario ha sido muy activo (basado en el número de mensajes enviados) o si su fecha de registro es reciente. El sistema utiliza el patrón **Strategy** y **Composite** para aplicar y combinar estos descuentos.
* **Exportar Conversaciones a PDF**: Los usuarios premium tienen la capacidad de exportar el historial de una conversación con un contacto a un archivo **PDF**. Esta funcionalidad utiliza la biblioteca **iTextPDF**.

---

## Arquitectura y Tecnologías

* **Lenguaje**: Java
* **Interfaz Gráfica**: Java Swing
* **Persistencia**:
    * Se utiliza un `ServicioPersistencia` proporcionado por una dependencia externa (`driverPersistencia`).
    * Se implementa el patrón **DAO (Data Access Object)** para aislar la lógica de acceso a datos (`AdaptadorUsuarioDAO`, `AdaptadorMensajeDAO`, etc.).
    * Se utiliza el patrón **Abstract Factory** (`FactoriaDAO`) para desacoplar la creación de los DAOs.
    * Se usa un **Pool de objetos** (`PoolDAO`) para cachear las entidades recuperadas y mejorar el rendimiento.
* **Dependencias Principales** (según `pom.xml`):
    * `com.toedter:jcalendar`: Para seleccionar fechas en la interfaz gráfica.
    * `com.itextpdf:itextpdf`: Para la generación de documentos PDF.
    * `tds:chat-window`: Posiblemente para componentes de la ventana de chat.
    * `umu.tds:driverPersistencia`: El driver para el servicio de persistencia.

---

## Estructura del Proyecto

El código fuente está organizado en los siguientes paquetes principales:

* `lanzador`: Contiene la clase `Lanzador` con el método `main` para iniciar la aplicación.
* `dominio`: Define las clases del modelo de negocio (p. ej., `Usuario`, `Grupo`).
* `vista`: Contiene todas las clases relacionadas con la interfaz de usuario (ventanas, diálogos y renderizadores).
* `controlador`: Contiene la clase `Controlador` que centraliza la lógica de la aplicación.
* `persistencia`: Incluye los adaptadores DAO y la lógica para interactuar con la base de datos.
* `excepciones`: Define excepciones personalizadas para gestionar errores específicos de la aplicación (p. ej., `ExcepcionLogin`, `ExcepcionRegistro`).

---

## Instalación y Ejecución

Para ejecutar la aplicación, sigue estos pasos:

1.  **Clonar el repositorio y abrirlo en un IDE**:
    Abre el proyecto en un entorno de desarrollo compatible con Maven, como IntelliJ IDEA o Eclipse. El IDE debería detectar el archivo `pom.xml` y descargar las dependencias necesarias automáticamente.

2.  **Localizar el punto de entrada**:
    Navega hasta el paquete `lanzador` y abre la clase `Lanzador.java`.

3.  **Ejecutar la aplicación**:
    Ejecuta el método `main` dentro de la clase `Lanzador`. Esto iniciará la aplicación y mostrará la ventana de **Login**, desde donde podrás registrar un nuevo usuario o iniciar sesión si ya tienes una cuenta.
