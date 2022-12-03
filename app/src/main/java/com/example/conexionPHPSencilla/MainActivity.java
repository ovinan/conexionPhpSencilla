package com.example.conexionPHPSencilla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// IMPORTANTE: Revisa el fichero BUILD.GRADLE para asegurar que
// incluye la dependencia de la libreria volley
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // Direccion IP del servidor, y ubicacion de las paginas PHP en el servidor
    // (en caso de que no esten directamente en la raiz del servidor.
    String URL_SERVIDOR = "http://192.168.0.181/android/";

    // Creamos los componentes que usaremos en el codigo: edittexts y botones
    EditText eNombre, eApellido, eDireccion, eTelefono, eCorreo, eSexo, eIdentificador;
    Button bCrear, bLimpiar, bBuscar;

    @Override
    // En la creacion de la actividad, relacionamos los componentes en codigo con los
    // componentes en el activity_main.xml, e incluimos el codigo que se ejecutara al
    // pulsar los botones LIMPIAR y BUSCAR.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relacionamosVistas();

        // Capturamos aqui la pulsacion del boton LIMPIAR
        // en lugar de utilizar el atributo conClick en el activity_main.xml:
        // Vaciamos todos los edittexts
        bLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eNombre.setText("");
                eApellido.setText("");
                eDireccion.setText("");
                eTelefono.setText("");
                eCorreo.setText("");
                eSexo.setText("");
                eIdentificador.setText("");
            }
        });
        // Capturamos aqui la actividad del boton BUSCAR
        // en lugar de utilizar el atributo conClick en el activity_main.xml:
        // Lanzamos la consulta contra la URL remota (llamando al metodo BUSCARUSUARIOS).
        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuarios(URL_SERVIDOR+"buscar_usuario.php?id="+ eIdentificador.getText()+"");
            }
        });
    }
    // Relacionamos los componentes en codigo, con los componentes en el activity_main.xml
    public void relacionamosVistas(){
        eNombre =(EditText)findViewById(R.id.nombre);
        eApellido =(EditText)findViewById(R.id.apellido);
        eDireccion =(EditText)findViewById(R.id.direccion);
        eTelefono =(EditText)findViewById(R.id.telefono);
        eCorreo =(EditText)findViewById(R.id.correo);
        eSexo =(EditText)findViewById(R.id.sexo);
        eIdentificador =(EditText)findViewById(R.id.id);
        bCrear =(Button) findViewById(R.id.btnCrear);
        bLimpiar =(Button)findViewById(R.id.btnLimpiar);
        bBuscar = (Button)findViewById(R.id.btnBuscar);
    }

    // Metodo que se ejecuta al pulsar el boton de CREARREGISTRO
    public void crearRegistro(View v){
        // Obtenemos el texto de los EditText
        final String nombre= eNombre.getText().toString();
        final String apellido= eApellido.getText().toString();
        final String direccion= eDireccion.getText().toString();
        final String telefono= eTelefono.getText().toString();
        final String correo= eCorreo.getText().toString();
        final String sexo= eSexo.getText().toString();

        // Construimos la direccion de la URL remota, pasando los parametros
        String url=URL_SERVIDOR+"ingreso.php?nombre="+nombre
            +"&apellido="+apellido
            +"&direccion="+direccion
            +"&telefono="+telefono
            +"&correo="+correo
            +"&sexo="+sexo;
        // Usando Volley, vamos a enviar la peticion a la URL remota, y recibir la respuesta,
        // para eso usaremos un StringRequest (porque la respuesta del servidor ante una operacion
        // de INSERT en la base de datos no va a ser compleja), que recibe 4 parametros:
        //  1. Metodo a utilizar: en nuestro caso, pasaremos los parametros con GET
        //  2. URL: Direccion del servidor remoto + servicio o API + Parametros
        //  3. Listener: Listener donde recibiremos la respuesta
        //  4. Listener donde recibiremos los errores (o null si los queremos ignorar)
        // En nuestro caso los listeners los crearemos en la propia creacion del StringRequest,
        // incluyendo el codigo a ejecutar en caso de que todo haya ido bien, o si hubiese errores.
        RequestQueue colaServidor= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
            Request.Method.GET,
            url,
            // Como segundo parametro, indicamos directamente el codigo a ejecutar si todo ha ido OK
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
                }
            },
            // Como tercer parametro, indicamos directamente el codigo a ejecutar si ha habido errores
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error comunicación",Toast.LENGTH_SHORT).show();
                }
            }
        );
        colaServidor.add(respuesta);
    }

    // Metodo que se ejecuta al pulsar el boton BUSCARUSUARIO
    public void buscarUsuarios(String URL){
        // Usando Volley, vamos a enviar la peticion a la URL remota, y recibir la respuesta.
        // En este caso usaremos un jsonArrayRequest, porque la respuesta del servidor
        // no va a ser simple, sino ser un JSON. Para crear un jsonArrayRequest hay que indicar
        // tres parametros:
        //  1. Direccion del servidor remoto al que le pasamos los parametros y nos devuelve el JSON
        //  2. listener donde recibiremos el JSON.
        //  3. Listener donde recibiremos los errores (o null si los queremos ignorar)
        // En nuestro caso, los listener los crearemos enla propia creacion del jsonArrayRequest,
        // y en lugar de utilizar otro metodo aparte, en dicha creacion pondremos el codigo para procesar ese JSON.
        RequestQueue colaServidor= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
            URL,
            // Codigo donde procesamos el JSON que nos devuelve el servidor: en este caso lo mostramos
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            eNombre.setText(jsonObject.getString("nombre"));
                            eApellido.setText(jsonObject.getString("apellido"));
                            eDireccion.setText(jsonObject.getString("direccion"));
                            eTelefono.setText(jsonObject.getString("telefono"));
                            eCorreo.setText(jsonObject.getString("correo"));
                            eSexo.setText(jsonObject.getString("sexo"));
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            },
            // Codigo donde mostramos el mensaje de error, en su caso
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error de Conexión", Toast.LENGTH_SHORT).show();
                }
            }
        );
        colaServidor.add(jsonArrayRequest);
    }
}
