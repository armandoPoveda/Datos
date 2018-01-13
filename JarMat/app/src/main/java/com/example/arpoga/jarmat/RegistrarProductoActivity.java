package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Producto;
import com.example.arpoga.jarmat.Model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class RegistrarProductoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_Nombre, text_Descripcion, text_Precio;
    private Spinner spinner_Categorias;
    private Button boton_Cancelar, boton_Guardar;

    private DatabaseReference bbddProdcutos, bbddUsuarios;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        text_Nombre = (EditText) findViewById(R.id.editText_Nombre);
        text_Descripcion = (EditText) findViewById(R.id.editText_Descripcion);
        text_Precio = (EditText) findViewById(R.id.editText_Precio);

        boton_Cancelar = (Button) findViewById(R.id.button_Cancelar);
        boton_Cancelar.setOnClickListener(this);
        boton_Guardar = (Button) findViewById(R.id.button_Guardar);
        boton_Guardar.setOnClickListener(this);

        spinner_Categorias = (Spinner) findViewById(R.id.spinner_Categorias);

        bbddProdcutos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));
        bbddUsuarios = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_Guardar:

                final String nombre = text_Nombre.getText().toString();
                final String descripcion = text_Descripcion.getText().toString();
                final String precio = text_Precio.getText().toString();
                final String categoria = spinner_Categorias.getSelectedItem().toString();
                final String uid = mAuth.getCurrentUser().getUid();

                if (TextUtils.isEmpty(nombre)

                        || TextUtils.isEmpty(descripcion)
                        || TextUtils.isEmpty(precio)) {

                    Toast.makeText(RegistrarProductoActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();

                } else {

                    //consulta por el campo uid
                    Query q = bbddUsuarios.orderByChild(getString(R.string.campo_uid)).equalTo(uid);

                    //escuchador para la query
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario usuario = null;
                            //recorremos la consulta del DataSnapshot
                            for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                                //cogemos los valores de la consulta de la clase usuario del uid unico
                                usuario = datasnapshot.getValue(Usuario.class);
                            }
                            //guardamos en una variable el alias
                            String alias = usuario.getAlias();

                            //registramos el producto con su clase y el alias
                            Producto producto = new Producto(nombre, descripcion, categoria, precio, uid, alias);
                            String clave = bbddProdcutos.push().getKey();
                            bbddProdcutos.child(clave).setValue(producto);
                            Toast.makeText(RegistrarProductoActivity.this, "Producto registrado: " +nombre , Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                break;

            case R.id.button_Cancelar:
                finish();
                break;
        }

    }
}
