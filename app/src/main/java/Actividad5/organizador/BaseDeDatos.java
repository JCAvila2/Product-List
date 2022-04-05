package Actividad5.organizador;

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

    @Override // Crear tabla
    public void onCreate(SQLiteDatabase base_de_datos) {
        String query = "CREATE TABLE " + NOMBRE_TABLA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT)";
        base_de_datos.execSQL(query);
    }

    @Override // Borrar tabla
    public void onUpgrade(SQLiteDatabase base_de_datos, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + NOMBRE_TABLA;
        base_de_datos.execSQL(query);
    }


    public boolean addDatos (String elemento) {
        Log.i("DB", "Agregando " + elemento);
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

    public Cursor getDatos() {
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "SELECT * FROM " + NOMBRE_TABLA;
        Cursor datos = base_de_datos.rawQuery(query, null);
        return datos;
    }

    public Cursor getID(String elemento) {
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + NOMBRE_TABLA + " WHERE " + COL1 + " = '" + elemento + "'";
        Cursor datos = base_de_datos.rawQuery(query, null);
        return datos;
    }

    public void actualizarElemento(String nuevoElemento, int id, String antiguoElemento) {
        Log.i("BASE DE DATOS", "Se actualizaron los datos en " + antiguoElemento);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "UPDATE " + NOMBRE_TABLA + " SET " + COL1 + " = '" + nuevoElemento + "' WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " = '" + antiguoElemento + "'";
        base_de_datos.execSQL(query);
    }

    public void eliminarElemento(int id, String elemento) {
        Log.i("BASE DE DATOS", "Se elimino " + elemento);
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "DELETE FROM " + NOMBRE_TABLA + " WHERE " + COL0 + " = '" + id + "' AND " + COL1 + " = '" + elemento + "'";
        base_de_datos.execSQL(query);
    }

    public void limpiarElementos() {
        Log.i("BASE DE DATOS", "Se eliminaron todos los elementos de la base de datos");
        SQLiteDatabase base_de_datos = this.getWritableDatabase();
        String query = "DELETE FROM " + NOMBRE_TABLA;
        base_de_datos.execSQL(query);
    }

}
