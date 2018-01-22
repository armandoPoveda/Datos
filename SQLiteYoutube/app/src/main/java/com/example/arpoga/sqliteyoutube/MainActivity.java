package com.example.arpoga.sqliteyoutube;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View view) {

        //lanzamos otro activity con el intent para registra profesores
        //también otro activity para alumnos
        // y otro activity para realizar consultas de la BBDD
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.buttonRegistrarProfesor:
                miIntent = new Intent(MainActivity.this, RegistroProfesorActivity.class);
        }
        if(miIntent != null){
            startActivity(miIntent);
        }
        switch (view.getId()){
            case R.id.buttonRegistarAlumno:
                miIntent = new Intent(MainActivity.this, RegistroAlumnoActivity.class);
        }
        switch (view.getId()){
            case R.id.buttonRegistrarAsignatura:
                miIntent = new Intent(MainActivity.this, RegistrarAsignaturaActivity.class);
        }
        if(miIntent != null){
            startActivity(miIntent);
        }
        switch (view.getId()){
            case R.id.buttonBorrarBBDD:
                //con el boton borrar llamamos al metodo borrar base de datos que nos hemos creado en este mismo activity
                borrarBD();
        }
        switch ((view.getId())){
            case  R.id.buttonConsultarRegistros:
                miIntent = new Intent(MainActivity.this, ConsultasActivity.class);
                startActivity(miIntent);
        }
    }

    //metodo para borrar la BBDD entera
    private void borrarBD() {

        //conexion a la base de datos
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getApplicationContext(),"usuarios.bd",null,1);

        //abrimos la base de datos con getWritableDatabase
        SQLiteDatabase db= conn.getWritableDatabase();

        //eliminamos la base de datos entera
        this.deleteDatabase("usuarios.bd");

        //mostramos un toas
        Toast.makeText(this, "Se borro toda la BBDD", Toast.LENGTH_SHORT).show();

        //cerramos la base de datos
        db.close();
    }
}
