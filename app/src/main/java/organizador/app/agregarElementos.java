package organizador.app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class agregarElementos extends AppCompatActivity {

    // Declaracion de variables
    BaseDeDatos db;
    verElementos ver_Elementos;
    EditText elemento, precio;
    Button guardar, cancelar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_elementos);
        getSupportActionBar().hide();

        db = new BaseDeDatos(this);
        ver_Elementos = new verElementos();
        elemento = findViewById(R.id.elemento_entrada);
        precio = findViewById(R.id.precio_entrada);
        guardar = findViewById(R.id.button_guardar);
        cancelar = findViewById(R.id.button_cancelar);

        // Listener boton guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nuevoElemento = elemento.getText().toString();
                String precio_ = precio.getText().toString();
                if (elemento.length() != 0 && precio_.length() != 0) {
                    AddDatos(nuevoElemento, precio_);
                    Intent volver = new Intent(agregarElementos.this, verElementos.class);
                    startActivity(volver);
                } else if ( nuevoElemento.length() == 0 && precio_.length() == 0) {
                    toastMensaje("Ingresa un elemento y un precio");
                } else if (precio_.length() == 0) {
                    toastMensaje("Ingresa un precio");
                } else {
                    toastMensaje("Ingresa un elemento");
                }
            }
        });

        // Listener boton cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent volver = new Intent(agregarElementos.this, verElementos.class);
                startActivity(volver);
            }
        });


    }

    // Agregar el elemento a la tabla
    public void AddDatos(String elemento, String precio_) {
        boolean insertarDatos = db.addElemento(elemento, precio_);
        if (insertarDatos) {
            toastMensaje("Los datos se guardaron correctamente");
        } else {
            toastMensaje("Algo ocurrio, los datos no se guardaron");
        }
    }

    // Metodo para mostrar un Toast
    private void toastMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }



} // termina la clase
