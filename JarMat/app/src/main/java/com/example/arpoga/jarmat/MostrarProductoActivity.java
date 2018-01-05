package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.arpoga.jarmat.Model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarProductoActivity extends AppCompatActivity {

   private ListView listView;
   private ArrayList<String> arrayList;
   private ArrayAdapter arrayAdapter;

   private DatabaseReference bbdd;
   private Query query;
   private FirebaseAuth mAuth;
   private String clave;
   private ArrayList<String> refProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_producto);

        listView = (ListView) findViewById(R.id.listView);
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        query = bbdd.orderByChild(getString(R.string.campo_uid)).equalTo(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                refProducto = new ArrayList<>();
                arrayList = new ArrayList<>();
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    //Nos quedamos con la clave del producto que nos devuelve la query
                    clave = datasnapshot.getKey();

                    //Recuperamos para cada datasnapshot un objeto producto
                    //del que recuperaremos su nombre para añadirlo al arraylist
                    Producto producto = datasnapshot.getValue(Producto.class);
                    arrayList.add(producto.getNombre());

                    //Añadimos las claves al arraylist de referencias de productos
                    refProducto.add(clave);
                }
                arrayAdapter = new ArrayAdapter(MostrarProductoActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);
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
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit:
                //devolvemos la posicion seleccionada
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int posicion = info.position;

                //Creamos un intent con la referencia del producto seleccionado - pasandole la posicion
                Intent i = new Intent(MostrarProductoActivity.this, ModificarProductoActivity.class);
                i.putExtra("CLAVE_PRODUCTO",refProducto.get(posicion));
                startActivity(i);

                break;

            case R.id.remove:
                bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos)+"/"+clave);
                bbdd.removeValue();

                break;
        }
        return super.onContextItemSelected(item);
    }
}
