package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarTodosProductosActivity extends AppCompatActivity  {

    private ListView listView;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_todos_productos);

        listView = (ListView) findViewById(R.id.lista);

        ArrayList<String> lista = (ArrayList<String>) getIntent().getStringArrayListExtra("Nombre");

        //Toast.makeText(MostrarTodosProductosActivity.this, "Array: "+ lista, Toast.LENGTH_SHORT).show();

        arrayAdapter = new ArrayAdapter(MostrarTodosProductosActivity.this, android.R.layout.simple_list_item_1, lista);

        listView.setAdapter(arrayAdapter);

    }
}
