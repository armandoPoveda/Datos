package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Favoritos;
import com.example.arpoga.jarmat.Model.Producto;
import com.example.arpoga.jarmat.Model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InfoProductosActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference bbdd, bbddUsuarios;
    private DatabaseReference bbddFavoritos;
    private TextView text_nombre, text_descripcion, text_precio;
    private Query query;
    private String key;
    private Button boton_Favoritos;
    private FirebaseAuth mAuth;
    private String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_productos);

        text_nombre = findViewById(R.id.editText_Nombre);
        text_descripcion = findViewById(R.id.editText_Descripcion);
        text_precio = findViewById(R.id.editText_Precio);
        boton_Favoritos = findViewById(R.id.button_Favoritos);
        boton_Favoritos.setOnClickListener(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        key = extras.getString("KEY");

        mAuth = FirebaseAuth.getInstance();
        bbddUsuarios = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
        bbddFavoritos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_favoritos));
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos)+"/"+key);
        Toast.makeText(getApplicationContext(), "alias "+key, Toast.LENGTH_SHORT).show();

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Toast.makeText(getApplicationContext(), "nombre "+nombre, Toast.LENGTH_SHORT).show();

                    Producto p = dataSnapshot.getValue(Producto.class);
                    text_nombre.setText(p.getNombre());
                    text_descripcion.setText(p.getDescripcion());
                    text_precio.setText(p.getPrecio());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_Favoritos:

                final String nombre = text_nombre.getText().toString();
                uid = mAuth.getCurrentUser().getUid();
                //consulta por el campo uid
                Query q = bbdd.orderByChild(getString(R.string.campo_uid)).equalTo(uid);

                //escuchador para la query
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Producto producto = null;
                        //recorremos la consulta del DataSnapshot
                        for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                            //cogemos los valores de la consulta de la clase usuario del uid unico
                            producto = datasnapshot.getValue(Producto.class);
                           uid = producto.getUid();
                        }

                        //registramos el producto con su clase y el alias
                        Favoritos favoritos = new Favoritos(nombre, uid);
                        String clave = bbddFavoritos.push().getKey();
                        bbddFavoritos.child(clave).setValue(favoritos);
                        Toast.makeText(InfoProductosActivity.this, "Producto añadido como favorito: " +nombre , Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

          break;
        }
    }
}
