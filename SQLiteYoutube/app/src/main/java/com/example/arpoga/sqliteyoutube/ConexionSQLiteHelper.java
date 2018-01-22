package com.example.arpoga.sqliteyoutube;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arpoga.sqliteyoutube.utilidades.utilidades;

/**
 * Created by arpoga on 29/11/2017.
 */

// esta clase es el adaptador que hara la conexion a la base de datos
    //esta clase extiende de SQLiteOpenHelper que habre una base de datos, la crea si no existe y actulizarla
public class ConexionSQLiteHelper extends SQLiteOpenHelper {



    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //el oncreate crea la base de datos con las tablas que le pasemos
    //creadas en una clase utilidades donde hemos creado los registros
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_ALUMNO);
        db.execSQL(utilidades.CREAR_TABLA_PROFESOR);
        db.execSQL(utilidades.CREAR_TABLA_ASIGNATURA);

    }

    //actulizar la bbdd
    //se carga la tabla y nos crea una nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS usuarios.bd");
        onCreate(db);

    }
}
