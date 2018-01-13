package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ModificarUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boton_Modificar, boton_Cancelar;
    private EditText text_Nombre, text_Apellidos, text_Direccion, text_Email;
    private TextView view_alias, view_uid;

    private FirebaseAuth mAuth;
    private DatabaseReference bbddUsuario;
    private Query query;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        boton_Modificar = findViewById(R.id.button_Modificar_Usuario);
        boton_Modificar.setOnClickListener(this);
        boton_Cancelar = findViewById(R.id.button_Cancelar);
        boton_Cancelar.setOnClickListener(this);

        text_Nombre = findViewById(R.id.editText_Nombre);
        text_Apellidos = findViewById(R.id.editText_Apellido);
        text_Direccion = findViewById(R.id.editText_Direccion);
        text_Email = findViewById(R.id.editText_Correo);

        view_alias = findViewById(R.id.textView_Alias);
        view_uid = findViewById(R.id.textView_Uid);


        Intent i = getIntent();
        Bundle extra = i.getExtras();
        String clave = extra.getCharSequence("clave").toString();

        bbddUsuario = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios)+"/"+clave);

        bbddUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    view_alias.setText(usuario.getAlias());
                    text_Email.setText(usuario.getCorreo());
                    view_uid.setText(usuario.getUid());
                    text_Nombre.setText(usuario.getNombre());
                    text_Apellidos.setText(usuario.getApellidos());
                    text_Direccion.setText(usuario.getDireccion());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button_Modificar_Usuario:

                String nombre = text_Nombre.getText().toString();
                String apellidos = text_Apellidos.getText().toString();
                String direccion = text_Direccion.getText().toString();
                String alias = view_alias.getText().toString();
                String correo = text_Email.getText().toString();
                String uid = view_uid.getText().toString();
                Usuario usuario = new Usuario(alias, nombre, apellidos, direccion, correo, uid);
                bbddUsuario.setValue(usuario);
                Toast.makeText(this, "Se han modificado los datos", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.button_Cancelar:
                finish();
                break;
        }

    }
}
