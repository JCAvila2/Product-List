package Actividad5.organizador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class editarElemento extends AppCompatActivity {

    // Declaracion de variables
    BaseDeDatos db;
    verElementos ver_Elementos;
    private EditText editar_Elemento;
    private Button guardarModificacion, eliminarElemento;
    private String elemento_seleccionado;
    private int ID_seleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_elementos);
        getSupportActionBar().hide();

        // Inicializacion de variables
        db = new BaseDeDatos(this);
        ver_Elementos = new verElementos();
        editar_Elemento = findViewById(R.id.editar_elemento);
        guardarModificacion = findViewById(R.id.guardar_modificacion);
        eliminarElemento = findViewById(R.id.eliminar_elemento);

        // Se obtiene el intent extra de verElementos
        Intent intentRecibido = getIntent();

        // Se obtiene el elemento que se paso como extra en elementoID
        ID_seleccionado = intentRecibido.getIntExtra("ID", -1);

        // Se obtiene el item que se paso como extra en elemento
        elemento_seleccionado = intentRecibido.getStringExtra("elemento");

        // Se coloca el elemento seleccionado en la barra para modificar
        editar_Elemento.setText(elemento_seleccionado);

        // Listeners para los botones
        guardarModificacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String elementoNuevo = editar_Elemento.getText().toString();
                if (!elementoNuevo.equals("")) {
                    db.actualizarElemento(elementoNuevo, ID_seleccionado, elemento_seleccionado);
                    toastMensaje("Se modificó " + elemento_seleccionado);
                    Intent volver = new Intent(editarElemento.this, verElementos.class);
                    startActivity(volver);
                } else {
                    toastMensaje("No se puede guardar un elemento vacio");
                }
            }
        });
        eliminarElemento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                db.eliminarElemento(ID_seleccionado, elemento_seleccionado);
                toastMensaje("Se elimino " + elemento_seleccionado);
                Intent volver = new Intent(editarElemento.this, verElementos.class);
                startActivity(volver);
            }
        });
    }

    private void toastMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
