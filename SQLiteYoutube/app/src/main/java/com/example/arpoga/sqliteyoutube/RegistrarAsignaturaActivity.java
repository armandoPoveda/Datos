package com.example.arpoga.sqliteyoutube;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arpoga.sqliteyoutube.utilidades.utilidades;

public class RegistrarAsignaturaActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText text_Nombre, text_Horas;
    private Button boton_Añadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_asignatura);

        text_Horas = findViewById(R.id.editText_Hora);
        text_Nombre = findViewById(R.id.editText_Nombre);
        boton_Añadir = findViewById(R.id.button_añadir_Asignatura);
        boton_Añadir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_añadir_Asignatura:

                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuarios2.bd", null, 1);

                //connectamos con getWritableDatabase
                SQLiteDatabase db = conn.getWritableDatabase();

                //Con contentValues añadimos datos a la BBDD
                ContentValues values = new ContentValues();

                if (text_Nombre.getText().length() == 0) {
                    Toast.makeText(this, "Introduce el nombre", Toast.LENGTH_SHORT).show();
                }
                if (text_Horas.getText().length() == 0){
                    Toast.makeText(this, "Introduce las horas", Toast.LENGTH_SHORT).show();
                }

                values.put(utilidades.CAMPO_NOMBRE, text_Nombre.getText().toString());
                values.put(utilidades.CAMPO_HORAS, text_Horas.getText().toString());
                Toast.makeText(this, "Se añadio la asignatura", Toast.LENGTH_SHORT).show();

                db.close();

                break;
        }
    }
}
