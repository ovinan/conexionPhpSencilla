🧪 Este es un ejemplo hipersencillo de app Android -realizada con Android Studio- que usa dos servicios web de un servidor remoto. 

💻 Tambien se incluye el codigo PHP de dichos servicios web, que se conectan a una base de datos para crear/recuperar informacion.

📋 En el directorio /app/ficheros php teneis tanto el codigo PHP de los servicios web, como la instruccion SQL para crear la tabla.

🔐 En el fichero conexion.php se especifican los parametros de conexion con la base de datos.

📱 Volviendo a Android Studio, en codigo java del fichero MainActivity.java se utiliza una constante para especificar la URL del servidor.


⚠️ MUY IMPORTANTE:
Revisa que en el fichero build.graddle (de la app) está la dependencia de la libreria volley, es decir, algo como lo siguiente

dependencies {
    ...
    implementation 'com.android.volley:volley:1.1.1'
    ...
}

🚀 MEJORAS:
- Controlar los tipos de datos: el programa devolvera un error si intentas almacenar texto en un campo que en la base de datos es numerico. 
  Puedes probarlo tu mismo introduciendo un texto en el apartado de numero de telefono.
- Registrar el identificador del usuario: la app realiza búsquedas en la base de datos por el campo ID, pero sin embargo la app no graba ese dato.
