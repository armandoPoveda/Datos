package com.example.arpoga.prueba3a;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button guardar1;
    private Button mostrar1;

    final static int RESQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guardar1 = (Button) findViewById(R.id.buttonGuardar);
        mostrar1 = (Button) findViewById(R.id.buttonMostrar);

        guardar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(), Main2Activity.class);
                startActivityForResult(i,RESQUEST_CODE);
            }
        });

        mostrar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                String dia = preferencias.getString("Dia", "");
                String hora = preferencias.getString("Hora", "");

                Toast.makeText(MainActivity.this, "La última toma de la pastilla fue el día"+dia+", a la hora:"+hora, Toast.LENGTH_LONG).show();

            }
        });

    }
}
