package organizador.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDeDatos extends SQLiteOpenHelper {

    // Declaracion de variables
    private static final String NOMBRE_TABLA = "tabla_elementos";
    private static final String COL0 = "ID";
    private static final String COL1 = "elemento";

    public BaseDeDatos(Context context) {
        super(context, NOMBRE_TABLA, null, 1);
    }

    @Override //Metodo para crear una tabla
    public void onCreate(SQLiteDatabase base_de_datos) {
        Log.i("BASE DE DATOS", "Se creo la tabla " + NOMBRE_TABLA);
        String query = "CREATE TABLE " + NOMBRE_TABLA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT)";
        base_de_datos.execSQL(query);
    }

    @Override //metodo para borrar una tabla
    public void onUpgrade(SQLiteDatabase base_de_datos, int i, int i1) {
        Log.i("BASE DE DATOS", "Se elimino la tabla " + NOMBRE_TABLA);
        String query = "DROP TABLE IF EXISTS " + NOMBRE_TABLA;
        base_de_datos.execSQL(query);
    }

    // metodo para agregar un elemento a una tabla
    public boolean addDatos (String elemento) {
        Log.i("DB", "Agregando " + elemento + " a " + NOMBRE_TABLA);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        ContentValues contenido = new ContentValues();
        contenido.put(COL1, elemento);
        long resultado = base_de_datos.insert(NOMBRE_TABLA, null, contenido);
        // Si los datos se pudieron guardar en la base de datos correctamente este metodo retorna true
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }
    }

    // metodo para obtener todos los elementos en una tabla
    public Cursor getDatos() {
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "SELECT * FROM " + NOMBRE_TABLA;
        Cursor datos = base_de_datos.rawQuery(query, null);
        return datos;
    }

    // metodo para obtener un elementode una tabla
    public Cursor getID(String elemento) {
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + NOMBRE_TABLA + " WHERE " + COL1 + " = '" + elemento + "'";
        Cursor datos = base_de_datos.rawQuery(query, null);
        return datos;
    }

    // metodo para modificar un elemento en una tabla
    public void actualizarElemento(String nuevoElemento, int id, String antiguoElemento) {
        Log.i("BASE DE DATOS", "Se actualizaron los datos en " + antiguoElemento + " de " + NOMBRE_TABLA);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "UPDATE " + NOMBRE_TABLA + " SET " + COL1 + " = '" + nuevoElemento + "' WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " = '" + antiguoElemento + "'";
        base_de_datos.execSQL(query);
    }

    // metodo para eliminar un elemento de una tabla
    public void eliminarElemento(int id, String elemento) {
        Log.i("BASE DE DATOS", "Se elimino " + elemento + " de " + NOMBRE_TABLA);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "DELETE FROM " + NOMBRE_TABLA + " WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " = '" + elemento + "'";
        base_de_datos.execSQL(query);
    }

    // metodo para eliminar una tabla
    public void limpiarElementos() {
        Log.i("BASE DE DATOS", "Se eliminaron todos los elementos de " + NOMBRE_TABLA);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "DELETE FROM " + NOMBRE_TABLA;
        base_de_datos.execSQL(query);
    }


    // metodo para conocer las tablas en la base de datos
    public Cursor conocerTablas() {
        Log.i("BASE DE DATOS", "Se actualizaron las tablas en la base de datos.");

        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name LIKE 'tabla_%' ";
        Cursor datos = base_de_datos.rawQuery(query, null);
        return datos;
    }



}
