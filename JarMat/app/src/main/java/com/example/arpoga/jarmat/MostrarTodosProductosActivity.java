package com.example.arpoga.jarmat;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class MostrarTodosProductosActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private Button boton_cancelar;

    private DatabaseReference bbdd;
    private String clave;
    private ArrayList<String> refProducto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_todos_productos);

        listView = (ListView) findViewById(R.id.lista);

        ArrayList<String> lista =  getIntent().getStringArrayListExtra("Nombre");

        arrayAdapter = new ArrayAdapter(MostrarTodosProductosActivity.this, android.R.layout.simple_list_item_1, lista);

        listView.setAdapter(arrayAdapter);

        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

        bbdd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refProducto = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    //Nos quedamos con la clave del producto que nos devuelve la query
                    Producto producto = datasnapshot.getValue(Producto.class);
                    //clave = producto.getNombre();
                    clave = datasnapshot.getKey();

                    //Añadimos las claves al arraylist de referencias de productos
                    refProducto.add(clave);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
      registerForContextMenu(listView);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ver:
                //devolvemos la posicion seleccionada
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                 int posicion = info.position;
                //Creamos un intent con la referencia del producto seleccionado - pasandole la posicion
                Intent i = new Intent(MostrarTodosProductosActivity.this, InfoProductosActivity.class);
                i.putExtra("KEY",refProducto.get(posicion));
                Toast.makeText(getApplicationContext(), "clave "+refProducto, Toast.LENGTH_SHORT).show();
                startActivity(i);
                break;


        }
        return super.onContextItemSelected(item);

    }

}
