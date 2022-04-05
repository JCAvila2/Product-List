package Actividad5.organizador;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declaracion de variables
    EditText nombreRegistro, emailRegistro;
    Button botonAcceder;
    public static String nombreUsuario, emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Inicializacion de Variables
        botonAcceder = findViewById(R.id.botonAcceder);
        nombreRegistro = findViewById(R.id.nombreEntrada);
        emailRegistro = findViewById(R.id.emailEntrada);

        // Listener boton acceder
        botonAcceder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("REGISTRO", "Click en boton Acceder");
                acceder();
            }
        });
    }

    private void acceder() {
        nombreUsuario = nombreRegistro.getText().toString();
        emailUsuario = emailRegistro.getText().toString();
        if ((!nombreUsuario.equals("")) && (!emailUsuario.equals(""))) {
            Log.i("REGISTRO", "Nombre: " + nombreUsuario + " guardado");
            Log.i("REGISTRO","Email" + emailUsuario + " guardado");
            // Luego de guardar los datos ingresados por el usuario se para a la siguiente actividad
            Intent ir_verElementos = new Intent(this, verElementos.class);
            startActivity(ir_verElementos);
        } else {
            toastMensaje("Nombre o email inválido.");
            Log.i("REGISTRO", "ERROR: Nombre o email vacio");
        }
    }

    public void toastMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}