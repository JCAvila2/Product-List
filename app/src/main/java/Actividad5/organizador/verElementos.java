package Actividad5.organizador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

public class verElementos extends AppCompatActivity {

    // Declaracion de Variables
    BaseDeDatos db;
    MainActivity registro;
    ListView gridLista;
    TextView mostrarCantidad;
    Button botonAgregar;
    public static int numeroDeElementos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_elementos);

        // Inicializacion de variables
        db = new BaseDeDatos(this);
        registro = new MainActivity();
        gridLista = findViewById(R.id.grid_lista);
        mostrarCantidad = findViewById(R.id.mostrar_cantidad);
        botonAgregar = findViewById(R.id.Boton_agregar);

        getSupportActionBar().setTitle(registro.nombreUsuario);
        mostrarLista();
        numeroDeElementos = gridLista.getAdapter().getCount();
        mostrarCantidad.setText(numeroDeElementos + "");


        // Listener del boton de agregar
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                agregarElemento();
            }
        });
    }

    private void mostrarLista() {
        // Se obtienen los datos almacenados en la base de datos y se agregan a un ArrayList para luego mostrarlos en pantalla por medio del ListView
        Cursor datos = db.getDatos();
        ArrayList<String> listaDeDatos = new ArrayList<>();
        while (datos.moveToNext()) {
            // Se obtiene el elemento almacenado en la primera columna de la tabla y se agrega en en ArrayList
            listaDeDatos.add(datos.getString(1));
        }
        // Se crea un adapter para actualizar el ListView
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeDatos);
        gridLista.setAdapter(adapter);

        // Se crea un listener para cada elemento en el ListView
        gridLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String elemento = adapterView.getItemAtPosition(i).toString();
                Cursor datos = db.getID(elemento);
                int elementoID = -1;
                while (datos.moveToNext()) {
                    elementoID = datos.getInt(0);
                }
                if (elementoID > -1) {
                    Log.i("verELEMENTOS", "Seleccionado " + elemento);
                    Intent editarPantalla = new Intent(verElementos.this, editarElemento.class);
                    editarPantalla.putExtra("ID", elementoID);
                    editarPantalla.putExtra("elemento", elemento);
                    startActivity(editarPantalla);
                } else {
                    toastMensaje("Error, este elemento no está guardado");
                }
            }
        });
    }

    public void agregarElemento() {
        // Llevar a la actividad para agregar elemento
        Log.i("verELEMENTOS", "Click en boton agregar");
        Intent ir_a_agregar_elementos = new Intent(verElementos.this, agregarElementos.class);
        startActivity(ir_a_agregar_elementos);
    }


    private void toastMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    // Barra menu esquina superior derecha
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salir: // Boton de salir
                AlertDialog.Builder preguntaSalir = new AlertDialog.Builder(this);
                preguntaSalir.setTitle("Salir");
                preguntaSalir.setMessage("¿" + registro.nombreUsuario + " está seguro que desea salir?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                preguntaSalir.setCancelable(false);
                preguntaSalir.show();
                break;
            case R.id.menu_eliminar: // Boton para eliminar todos los elementos
                AlertDialog.Builder preguntaEliminarTodo = new AlertDialog.Builder(this);
                preguntaEliminarTodo.setTitle("Eliminar Todo");
                preguntaEliminarTodo.setMessage("¿" + registro.nombreUsuario + " está seguro que desea eliminar permanentemente todos los elementos?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        db.limpiarElementos();
                        mostrarLista();
                        numeroDeElementos = gridLista.getAdapter().getCount();
                        mostrarCantidad.setText(numeroDeElementos + "");
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                preguntaEliminarTodo.setCancelable(false);
                preguntaEliminarTodo.show();
                break;
            case R.id.menu_infoRegistro:
                Context context;
                AlertDialog.Builder alertaInfoRegistro = new AlertDialog.Builder(this);
                alertaInfoRegistro.setTitle("Informacion");
                alertaInfoRegistro.setMessage("Los datos que ingresaste en el registro son: \nNombre: " + registro.nombreUsuario + "\nEmail: " + registro.emailUsuario);
                alertaInfoRegistro.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                alertaInfoRegistro.show();
                break;
            case R.id.theme:
                cambiarTema();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cambiarTema() {
        String userDefaultMode = getResources().getString(R.string.modo);
        if (userDefaultMode.equals("Oscuro")) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
