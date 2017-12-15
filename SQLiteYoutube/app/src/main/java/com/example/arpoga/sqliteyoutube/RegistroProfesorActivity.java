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

public class RegistroProfesorActivity extends AppCompatActivity implements View.OnClickListener {

   private EditText idP, nombreP, edadP, cursoP, cicloP, despachoP;
   private Button guardarProfesor, eliminarProfesor, buscarProfesor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesor);

        idP = (EditText) findViewById(R.id.editTextId);
        nombreP = (EditText) findViewById(R.id.editTextNombre);
        edadP = (EditText) findViewById(R.id.editTextEdad);
        cursoP = (EditText) findViewById(R.id.editTextCurso);
        cicloP = (EditText) findViewById(R.id.editTextCiclo);
        despachoP = (EditText) findViewById(R.id.editTextDespacho);

        guardarProfesor = (Button) findViewById(R.id.buttonRegistrar);
        guardarProfesor.setOnClickListener(this);
        eliminarProfesor = (Button) findViewById(R.id.buttonEliminarProfesor);
        eliminarProfesor.setOnClickListener(this);
        buscarProfesor = (Button) findViewById(R.id.buttonBuscarProfesor);
        buscarProfesor.setOnClickListener(this);
        eliminarProfesor.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonRegistrar) {
            //metodo para registrar profesor
            registrarProfesor();
        }
        if (view.getId() == R.id.buttonEliminarProfesor) {
            //metodo para borrar profesor
            deleteProfesor();
        }
        if (view.getId() == R.id.buttonBuscarProfesor) {
            //metodo para buscar profesor
            findProfesor();
        }
    }

    private void registrarProfesor() {

        //hacemos la conxeión con la BBDD
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios.bd", null, 1);

        //connectamos con getWritableDatabase
        SQLiteDatabase db = conn.getWritableDatabase();

        //Con contentValues añadimos datos a la BBDD
        ContentValues values = new ContentValues();

        //comprobamos que los datos introducidos no estén vacios
        if (nombreP.getText().length() == 0) {
            Toast.makeText(this, "Introduce el nombre", Toast.LENGTH_SHORT).show();
        }
            if (edadP.getText().length() == 0){
                Toast.makeText(this, "Introduce la edad", Toast.LENGTH_SHORT).show();
            }
                if (cursoP.getText().length() == 0){
                    Toast.makeText(this, "Introduce el curso", Toast.LENGTH_SHORT).show();
                }
                    if (cicloP.getText().length() == 0){
                        Toast.makeText(this, "Introduce el ciclo", Toast.LENGTH_SHORT).show();
                    }
                        if (despachoP.getText().length() == 0){
                            Toast.makeText(this, "Introduce el despacho", Toast.LENGTH_SHORT).show();

                        }else {

                     //recogemos los datos introducidos y los guardamos en el contentValues con un pu
                            //le pasamos el registro que queramos que nos guarde y el campo de texto de donde recoge el valor
                    values.put(utilidades.CAMPO_NOMBRE, nombreP.getText().toString());
                    values.put(utilidades.CAMPO_EDAD, edadP.getText().toString());
                    values.put(utilidades.CAMPO_CURSO, cursoP.getText().toString());
                    values.put(utilidades.CAMPO_CICLO, cicloP.getText().toString());
                    values.put(utilidades.CAMPO_DESPACHO, despachoP.getText().toString());

                    //con la sentencia insert, insertamos en la tabla profesores los valores guardados en el contentValues
                    Long idResultanteP = db.insert(utilidades.TABLA_PROFESOR, utilidades.CAMPO_NOMBRE, values);

                    //mostramos un toast con el resultado por el nombre guardado
                    Toast.makeText(getApplicationContext(), "Id Profesor añadido " + idResultanteP, Toast.LENGTH_SHORT).show();
                    //cerramos la conexion
                    db.close();
                }
        }

        //método para borrar un profesor por la id
    private void deleteProfesor() {

        //conectamos con la bbdd
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(),"usuarios.bd",null,1);

        //hacemos la conexion
        SQLiteDatabase db=conn.getWritableDatabase();

        //arraylist donde le pasamos la id y con Strin.valueof convertimos el String en número
        String[] parametros = {String.valueOf(idP.getText().toString())};

        //delete, primero le pasamos un string que es la tabla de datos de la utilidades
        //la clausula donde no sabemos la id
        // y por último el arraylist para que nos busque en la bbdd por la id que le pasamos en el campo id
        db.delete(utilidades.TABLA_PROFESOR,"_id=?", parametros);

        //lanzamos un toast diciendo la id que se ha eliminado
        Toast.makeText(this, "Se ha eliminado el registro con id: " + idP.getText().toString(), Toast.LENGTH_SHORT).show();

        //cerramos la conexión
        db.close();
    }

    //método para buscar profesores por la id
   private void findProfesor() {

        //conectamos con la bbdd
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(),"usuarios.bd",null,1);

        //abrimos la bbdd
        SQLiteDatabase db=conn.getReadableDatabase();

        //creamos dos arraylist el primero para que recoga el campo ID de la clase utilidades
        String[] parametros = {String.valueOf(idP.getText().toString())};
        //este para que nos muestre los datos del id que se ha encontrado en sus campos correspondientes
        String[] campos = {utilidades.CAMPO_NOMBRE, utilidades.CAMPO_EDAD,utilidades.CAMPO_CICLO, utilidades.CAMPO_CURSO, utilidades.CAMPO_DESPACHO};

        //try catch porque si comprobamos y no existe el error nos da un Exception, en un try catch y le lanzamos un toast si no existe
        try{
            //creamos un Cursor para hacer la consulta con query
            Cursor cursor = db.query(utilidades.TABLA_PROFESOR, campos, "_id=?", parametros, null, null, null);

            //moveTofirst mueve al siguiente campo
            cursor.moveToFirst();

            //getString nos devuelve los campos del registro
            nombreP.setText(cursor.getString(0));
            edadP.setText(cursor.getString(1));
            cicloP.setText(cursor.getString(2));
            cursoP.setText(cursor.getString(3));
            despachoP.setText(cursor.getString(4));

            //lanzamos un toast con la id del profesor que se encontró
            Toast.makeText(this, "Se encontro un profesor con la ID: " + idP.getText().toString(), Toast.LENGTH_SHORT).show();

            //cerramos el cursor
            cursor.close();

            //si hemos encontrado algún id ponemos el botón eliminar profesor a true por si queremos borrar el registro
            eliminarProfesor.setEnabled(true);
        }catch (Exception e){

            //lanzamos el toast si no existe ningún id en la BBDD
            Toast.makeText(this, "La ID consultada no existe", Toast.LENGTH_SHORT).show();

            //llamamos al metodo para borrar el dato introducido el id si no existe
            Limpiar();
        }

    }

    //metodo para poner el id sin datos
    private void Limpiar() {
        idP.setText("");
    }
}
