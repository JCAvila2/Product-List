package organizador.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class verCategorias extends AppCompatActivity {

    // Declaracion de variables
    BaseDeDatos db;
    ListView gridLista;
    TextView mostrarCantidad;
    Button botonAgregar;
    public static int numeroDeElementos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_categorias);

        // Inicializacion de variables
        db = new BaseDeDatos(this);
        gridLista = findViewById(R.id.grid_lista2);
        mostrarCantidad = findViewById(R.id.mostrar_cantidad2);
        botonAgregar = findViewById(R.id.Boton_agregar2);


        mostrarLista();

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent volver = new Intent(verCategorias.this, verElementos.class);
                startActivity(volver);
            }
        });

        // Listener para cada elemento del ListView
        gridLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                String tabla_seleccionada = (String) (gridLista.getItemAtPosition(posicion));
                Toast.makeText(verCategorias.this, "Click en: " + tabla_seleccionada, Toast.LENGTH_SHORT).show();
            }
        });

    } // Termina onCreate


    @SuppressLint("Range")
    private void mostrarLista() {
        ArrayList<String> listaDeDatos = new ArrayList<>();
        Cursor datos = db.conocerTablas();

        if (datos.moveToFirst()) {
            while(!datos.isAfterLast()) {
                listaDeDatos.add(datos.getString(datos.getColumnIndex("name")) );
                datos.moveToNext();
            }
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeDatos);
        gridLista.setAdapter(adapter);
    } // Termina mostrar lista



} //termina la clase
