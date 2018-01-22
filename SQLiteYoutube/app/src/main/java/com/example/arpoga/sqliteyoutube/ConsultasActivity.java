package com.example.arpoga.sqliteyoutube;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpoga.sqliteyoutube.utilidades.utilidades;

import java.sql.ResultSet;
import java.util.ArrayList;


public class ConsultasActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Curso, Ciclo;
    private Button botonTodosAlumnos, botonTodosProfesores, botonAlumnos, botonProfesores, ProfesoresAlumnos, boton_Asignaturas;

    ListView LV;
    ArrayAdapter Adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        LV = (ListView) findViewById(R.id.Lista);

        Curso = (EditText) findViewById(R.id.editTextCurso);
        Ciclo = (EditText) findViewById(R.id.editTextCiclo);

        botonAlumnos = (Button) findViewById(R.id.buttonMostarAlumnos);
        botonAlumnos.setOnClickListener(this);
        botonProfesores = (Button) findViewById(R.id.buttonMostrarProfesores);
        botonProfesores.setOnClickListener(this);
        botonTodosAlumnos = (Button) findViewById(R.id.buttonTodosAlumnos);
        botonTodosAlumnos.setOnClickListener(this);
        botonTodosProfesores = (Button) findViewById(R.id.buttonTodosProfesores);
        botonTodosProfesores.setOnClickListener(this);
        ProfesoresAlumnos = (Button) findViewById(R.id.buttonMostrarTodos);
        ProfesoresAlumnos.setOnClickListener(this);
        boton_Asignaturas = (Button) findViewById(R.id.buttonMostrarAsignaturas);
        boton_Asignaturas.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonMostarAlumnos) {
            buscarAlumnos();
        }

        if (view.getId() == R.id.buttonMostrarProfesores) {
            buscarProfesores();
        }

        if (view.getId() == R.id.buttonTodosAlumnos) {
            buscarTodosAlumnos();
        }

        if (view.getId() == R.id.buttonTodosProfesores) {
            buscarTodosProfesores();
        }

        if (view.getId() == R.id.buttonMostrarTodos) {
            MostrarTodos();
        }
        if (view.getId() == R.id.buttonMostrarAsignaturas) {
            mostrarAsignaturas();
        }
    }


    public void buscarTodosProfesores() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);


        SQLiteDatabase db = conn.getReadableDatabase();

        //arraylist para añadir profesores de la tabla
        ArrayList<String> profesores = new ArrayList<>();

        //creamos un Cursor para realizar una consulta con query, pasandole la tabla profesores
        Cursor cursor = db.query(utilidades.TABLA_PROFESOR, null, null, null, null, null, null);

        //si cursor es distino de null y curso mueve al siguiente dato
        if (cursor != null && cursor.moveToFirst()) {

            //haz añadir profesores al arraylist cogiendo el primer String que será el nombre
            do {
                profesores.add(cursor.getString(1));

                //mientras se cumpla la condicón moverá al siguiente registro e ira añadiendo nombres al arraylist
            } while (cursor.moveToNext());
        }

        //añadimos al adaptador el arraylist profesores para que nos los muestre en un list view
        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesores);

        //añadimos al listview el adaptador con setAdapter
        LV.setAdapter(Adaptador);
        //cerramos la conexión
        db.close();

    }

    //aquí hacemos lo mismo que en el método de buscar todos los profesores pero para los alumnos
    private void buscarTodosAlumnos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<String> alumnos = new ArrayList<>();

        Cursor cursor = db.query(utilidades.TABLA_ALUMNO, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                alumnos.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alumnos);

        LV.setAdapter(Adaptador);

        db.close();
    }

    private void buscarProfesores() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] campos = {utilidades.CAMPO_NOMBRE};

        //si Ciclo y Curso esta vacio nos muestra un toast para que introduzca datos en los campos vacios
        if(TextUtils.isEmpty(Ciclo.getText().toString()) && TextUtils.isEmpty(String.valueOf(Curso.getText().toString()))){

            Toast.makeText(this, "Introduce algún dato", Toast.LENGTH_SHORT).show();

        }else{
            //si ciclo y curso son distintos de null
            if (Curso != null && Ciclo != null) {

                ArrayList<String> profesor = new ArrayList<>();

                //array para guardar todos los registros que coincidan con Curso y Ciclo
                String[] parametros = {String.valueOf(Curso.getText().toString()), Ciclo.getText().toString()};

                //creamos el cursor para realizar una consulta
                //donde busque en la tabla profesores los campos nombre de los registros donde curso y ciclo coincidan con los introducidos y los guarde en el array
                Cursor cursor = db.query(utilidades.TABLA_PROFESOR, campos, "curso=? and ciclo=?", parametros, null, null, null);

                //si cursor es igual a null que nos muestre un toast
                if(cursor == null){
                    Toast.makeText(this, "no hay datos", Toast.LENGTH_SHORT).show();
                }else {
                    //si cursor es distinto de null y mueve al primero
                    if (cursor != null && cursor.moveToFirst()) {

                        //haz añade profesor congiendo el String en la posición 0
                        do {
                            profesor.add(cursor.getString(0));

                            //mientras curso mueva al siguiente registro
                        } while (cursor.moveToNext());

                        //rellenamos el listview
                        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesor);

                        LV.setAdapter(Adaptador);
                    }
                }
            }
        }
                //Aquí hacemos la consulta para los registros que coincidan con el curso introducido
            if (Curso != null && TextUtils.isEmpty(Ciclo.getText().toString()))  {

                    ArrayList<String> profesor2 = new ArrayList<>();

                    String[] parametros2 = {String.valueOf(Curso.getText().toString())};

                    Cursor cursor = db.query(utilidades.TABLA_PROFESOR, campos, "curso=?", parametros2, null, null, null);


                    if (cursor != null && cursor.moveToFirst()) {

                        do {
                            profesor2.add(cursor.getString(0));
                        } while (cursor.moveToNext());

                        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesor2);

                        LV.setAdapter(Adaptador);
                    }
                }

        //Aquí hacemos la consulta para los registros que coincidan con el ciclo introducido
        if (Ciclo != null && TextUtils.isEmpty(String.valueOf(Curso.getText().toString()))) {

                ArrayList<String> profesor3 = new ArrayList<>();

                String[] parametros3 = {Ciclo.getText().toString()};

                Cursor cursor = db.query(utilidades.TABLA_PROFESOR, campos, "ciclo=?", parametros3, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {

                    do {
                        profesor3.add(cursor.getString(0));
                    } while (cursor.moveToNext());

                    Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesor3);

                    LV.setAdapter(Adaptador);
                }
            }

        db.close();
    }

    //metodo para buscar alumnos hacemos lo mismo que con profesores
    private void buscarAlumnos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] campos = {utilidades.CAMPO_NOMBRE};

        //primero que los rgistros que coincidan con el ciclo y el curo introducidos
        if(TextUtils.isEmpty(Ciclo.getText().toString()) && TextUtils.isEmpty(String.valueOf(Curso.getText().toString()))){

            Toast.makeText(this, "Introduce algún dato", Toast.LENGTH_SHORT).show();

        }else {
            if (Curso != null && Ciclo != null) {

                ArrayList<String> alumno = new ArrayList<>();

                String[] parametros = {String.valueOf(Curso.getText().toString()), Ciclo.getText().toString()};

                Cursor cursor = db.query(utilidades.TABLA_ALUMNO, campos, "curso=? and ciclo=?", parametros, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {

                    do {
                        alumno.add(cursor.getString(0));
                    } while (cursor.moveToNext());

                    Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alumno);

                    LV.setAdapter(Adaptador);
                }
            }
        }

        //registros que conincidan con curso
        if (Curso != null && TextUtils.isEmpty(Ciclo.getText().toString()) ) {

            ArrayList<String> alumno2 = new ArrayList<>();

            String[] parametros2 = {String.valueOf(Curso.getText().toString())};

            Cursor cursor = db.query(utilidades.TABLA_ALUMNO, campos, "curso=?", parametros2, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    alumno2.add(cursor.getString(0));
                } while (cursor.moveToNext());

                Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alumno2);

                LV.setAdapter(Adaptador);
            }
        }

        //registros que coincidan con ciclo
        if (Ciclo != null && TextUtils.isEmpty(String.valueOf(Curso.getText().toString()))) {

            ArrayList<String> alumno3 = new ArrayList<>();

            String[] parametros3 = {Ciclo.getText().toString()};

            Cursor cursor = db.query(utilidades.TABLA_ALUMNO, campos, "ciclo=?", parametros3, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    alumno3.add(cursor.getString(0));
                } while (cursor.moveToNext());

                Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alumno3);

                LV.setAdapter(Adaptador);
            }

        }
        db.close();
    }

    //mostramos las dos tablas con todos sus registros por el nombre
    private void MostrarTodos() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<String> todos = new ArrayList<>();

        String[] campos = {utilidades.CAMPO_NOMBRE};

        Cursor cursor = db.query(utilidades.TABLA_PROFESOR, campos, null, null, null, null, null);
        Cursor cursor2 = db.query(utilidades.TABLA_ALUMNO, campos, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            do {
                todos.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        if (cursor2 != null && cursor2.moveToFirst()) {

            do {
                todos.add(cursor2.getString(0));
            } while (cursor2.moveToNext());
        }
        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todos);

        LV.setAdapter(Adaptador);

        db.close();
    }

    public void mostrarAsignaturas(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios2.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<String> asignaturas = new ArrayList<>();

        Cursor cursor = db.query(utilidades.TABLA_ASIGNATURA, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                asignaturas.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        Adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, asignaturas);

        LV.setAdapter(Adaptador);

        db.close();
    }
}
