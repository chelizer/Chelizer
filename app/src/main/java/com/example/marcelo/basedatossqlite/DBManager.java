package com.example.marcelo.basedatossqlite;

/**
 * Created by Marcelo on 12/11/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

public class DBManager {

    public final static String nombreTabla = "Aprendices";
    public final static String cnId = "_id";
    public final static String cnNombre = "Nombre";
    public final static String cnApellido = "Apellido";
    public final static String cnDireccion = "Direccion";
    public final static String cnEdad = "Edad";

    /* create table Aprendices (
        _ID integer primary key autoincrement,
        Nombre text not null,
        Apellido text not null,
        Direccion text not null,
        Edad text );
     */

    public final static String crearTabla =
            "create table "+nombreTabla+" ("
                    +cnId+" integer primary key autoincrement,"
                    +cnNombre+" text not null,"
                    +cnApellido+" text not null,"
                    +cnDireccion+" text not null,"
                    +cnEdad+" text);";

    public final static String borrarRegistros="Delete from "+nombreTabla+"";

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context)
    {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insertar(String Nombre, String Apellido, String Direccion, String Edad)
    {
        ContentValues values = getContentValues(Nombre, Apellido, Direccion, Edad);
        db.insert(nombreTabla, null, values);
    }

    public void borrarRegistros(){
        db.execSQL(borrarRegistros);
    }

    @NonNull //selecciono
    // selecciono todo el content values/clic derecho/refactor/extract/method
    private ContentValues getContentValues(String Nombre, String Apellido, String Direccion, String Edad) {
        ContentValues values = new ContentValues();
        values.put(cnNombre,Nombre);
        values.put(cnApellido,Apellido);
        values.put(cnDireccion,Direccion);
        values.put(cnEdad, Edad);
        return values;
    }

    public Cursor consultaAprendices(){

        String[] columnas = new String[]{cnId,cnNombre,cnApellido,cnDireccion,cnEdad};
        return db.query(nombreTabla,columnas,null,null,null,null,null);
    }

    public void eliminar(String nombre){
        db.delete(nombreTabla,cnNombre+"=?",new String[]{nombre});
    }

    public void actualizar(String nombre, String edad){
        //ContentValues values = getContentValues(null,null,null,edad);
        ContentValues values = new ContentValues();
        values.put(cnEdad, edad);
        db.update(nombreTabla, values, cnNombre + "=?", new String[]{nombre});
    }

    public Cursor buscar(String nombre){

        String sql="SELECT * FROM Aprendices WHERE Nombre LIKE '%"+nombre+"'";
       return db.rawQuery(sql,null);


    }


}

