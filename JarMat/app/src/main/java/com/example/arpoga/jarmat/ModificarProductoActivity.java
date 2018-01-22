package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Producto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ModificarProductoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_nombre, text_descripcion, text_precio;
    private Spinner spinner_categoria;
    private Button boton_Guardar, boton_Cancelar;
    private String alias,uid;

    private DatabaseReference bbdd;
    private DatabaseReference bbdd1;
    private FirebaseAuth mAuth;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        text_nombre = (EditText) findViewById(R.id.editText_Nombre);
        text_descripcion = (EditText) findViewById(R.id.editText_Descripcion);
        text_precio = (EditText) findViewById(R.id.editText_Precio);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_Categorias);


        boton_Cancelar = (Button) findViewById(R.id.button_Cancelar);
        boton_Cancelar.setOnClickListener(this);
        boton_Guardar = (Button) findViewById(R.id.button_Guardar);
        boton_Guardar.setOnClickListener(this);

       // bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));
        //bbdd1 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));


        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        extras = i.getExtras();
        String clave_producto = extras.getString("CLAVE_PRODUCTO");

       bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos) + "/" + clave_producto);

        bbdd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Producto p = dataSnapshot.getValue(Producto.class);
                Log.d("PRODUCTO:", p.toString());
                text_nombre.setText(p.getNombre());
                text_descripcion.setText(p.getDescripcion());
                text_precio.setText(p.getPrecio());
                alias = p.getAlias();
                uid = p.getUid();
                switch (p.getCategoria()) {
                    case "Electronica":
                        spinner_categoria.setSelection(0);
                        break;
                    case "Hogar":
                        spinner_categoria.setSelection(1);
                        break;
                    case "Coches":
                        spinner_categoria.setSelection(2);
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button_Guardar:
                String nombre = text_nombre.getText().toString();
                String descripcion = text_descripcion.getText().toString();
                String precio = text_precio.getText().toString();
                String categoria = spinner_categoria.getSelectedItem().toString();
                Producto p = new Producto(nombre,descripcion,categoria,precio,uid,alias);
                bbdd.setValue(p);
                finish();
                break;

            case R.id.button_Cancelar:
                finish();
                break;
        }

    }
}
