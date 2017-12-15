package com.example.arpoga.sqliteyoutube;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arpoga.sqliteyoutube.utilidades.utilidades;

public class RegistroAlumnoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText idA, edadA, nombreA, cicloA, cursoA, notaA;
    private Button guardarAlumno, eliminarAlumno, buscarAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumno);

        edadA = (EditText) findViewById(R.id.editTextEdad);
        idA = (EditText) findViewById(R.id.editTextId);
        nombreA = (EditText) findViewById(R.id.editTextNombre);
        cicloA = (EditText) findViewById(R.id.editTextCiclo);
        cursoA = (EditText) findViewById(R.id.editTextCurso);
        notaA = (EditText) findViewById(R.id.editTextnota);

        guardarAlumno = (Button) findViewById(R.id.buttonRegistrar);
        guardarAlumno.setOnClickListener(this);
        eliminarAlumno = (Button) findViewById(R.id.buttonEliminarAlumno);
        eliminarAlumno.setOnClickListener(this);
        eliminarAlumno.setEnabled(false);
        buscarAlumno = (Button) findViewById(R.id.buttonBuscarAlumno);
        buscarAlumno.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonRegistrar) {
            registrarAlumno();
        }

        if (view.getId() == R.id.buttonEliminarAlumno) {
            deleteAlumno();
        }
        if (view.getId() == R.id.buttonBuscarAlumno) {
            findAlumno();
        }
    }

    private void registrarAlumno() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (nombreA.getText().length() == 0) {
            Toast.makeText(this, "Introduce el nombre", Toast.LENGTH_SHORT).show();
        }
        if (edadA.getText().length() == 0) {
            Toast.makeText(this, "Introduce la edad", Toast.LENGTH_SHORT).show();
        }
        if (cursoA.getText().length() == 0) {
            Toast.makeText(this, "Introduce el curso", Toast.LENGTH_SHORT).show();
        }
        if (cicloA.getText().length() == 0) {
            Toast.makeText(this, "Introduce el ciclo", Toast.LENGTH_SHORT).show();
        }
        if (notaA.getText().length() == 0) {
            Toast.makeText(this, "Introduce la nota", Toast.LENGTH_SHORT).show();
        } else {
            values.put(utilidades.CAMPO_NOMBRE, nombreA.getText().toString());
            values.put(utilidades.CAMPO_EDAD, edadA.getText().toString());
            values.put(utilidades.CAMPO_CURSO, cursoA.getText().toString());
            values.put(utilidades.CAMPO_CICLO, cicloA.getText().toString());
            values.put(utilidades.CAMPO_NOTA, notaA.getText().toString());

            Long IdResultante = db.insert(utilidades.TABLA_ALUMNO, utilidades.CAMPO_NOMBRE, values);

            Toast.makeText(getApplicationContext(), "Id Alumno añadido: " + IdResultante, Toast.LENGTH_SHORT).show();
            db.close();
        }

    }

    public void deleteAlumno() {


        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametros = {String.valueOf(idA.getText().toString())};

        db.delete(utilidades.TABLA_ALUMNO, "_id=?", parametros);

        Toast.makeText(this, "Se ha eliminado el registro con id: " + idA.getText().toString(), Toast.LENGTH_SHORT).show();

        db.close();
    }

    private void findAlumno() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "usuarios.bd", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {String.valueOf(idA.getText().toString())};
        String[] campos = {utilidades.CAMPO_NOMBRE, utilidades.CAMPO_EDAD, utilidades.CAMPO_CICLO, utilidades.CAMPO_CURSO, utilidades.CAMPO_NOTA};

        //ponemos un try catch por si el usuario no existe que no nos mande un error y nos diga que el usuario no existe
        try {
            Cursor cursor = db.query(utilidades.TABLA_ALUMNO, campos, "_id=?", parametros, null, null, null);
            cursor.moveToFirst();
            nombreA.setText(cursor.getString(0));
            edadA.setText(cursor.getString(1));
            cicloA.setText(cursor.getString(2));
            cursoA.setText(cursor.getString(3));
            notaA.setText(cursor.getString(4));
            Toast.makeText(this, "Se encontro un alumno con la ID: " + idA.getText().toString(), Toast.LENGTH_SHORT).show();
            eliminarAlumno.setEnabled(true);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "La ID consultada no existe", Toast.LENGTH_SHORT).show();
            Limpiar();
        }


    }

    private void Limpiar() {
        idA.setText("");
    }
}
