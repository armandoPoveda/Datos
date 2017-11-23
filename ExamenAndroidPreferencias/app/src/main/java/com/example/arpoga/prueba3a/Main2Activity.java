package com.example.arpoga.prueba3a;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    private EditText Dia1, Hora1;
    private Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Dia1 = (EditText) findViewById(R.id.editTextDia);
        Hora1 = (EditText) findViewById(R.id.editTextHora);
        atras = (Button) findViewById(R.id.buttonAtras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("Dia", Dia1.getText().toString());
                editor.putString("Hora", Hora1.getText().toString());
                editor.commit();

                Intent i = getIntent();
                setResult(RESULT_OK, i);
                finish();
            }
        });





    }
}
