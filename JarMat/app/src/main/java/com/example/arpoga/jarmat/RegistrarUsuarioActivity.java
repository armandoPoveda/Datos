package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arpoga.jarmat.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_Alias, text_Nombre, text_Direccion, text_Apellidos, text_Correo, text_Password, text_Password_Repetida;
    private Button boton_Registrar;

    private FirebaseAuth mAuth;
    private DatabaseReference bbddUsuario;
    private Query query;
    private String alias, nombre, direccion, apellidos, correo, password, passwordRepetida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        text_Alias = (EditText) findViewById(R.id.editText_Alias);
        text_Nombre = (EditText) findViewById(R.id.editText_Nombre);
        text_Apellidos = (EditText) findViewById(R.id.editText_Apellido);
        text_Direccion = (EditText) findViewById(R.id.editText_Direccion);
        text_Correo = (EditText) findViewById(R.id.editText_Correo);
        text_Password = (EditText) findViewById(R.id.editText_Password);
        text_Password_Repetida = (EditText) findViewById(R.id.editText_Repetir_Password);

        boton_Registrar = (Button) findViewById(R.id.button_Guardar_Usuario);
        boton_Registrar.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        bbddUsuario = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_Guardar_Usuario:

                alias = text_Alias.getText().toString();
                nombre = text_Nombre.getText().toString();
                apellidos = text_Apellidos.getText().toString();
                direccion = text_Direccion.getText().toString();
                correo = text_Correo.getText().toString();
                password = text_Password.getText().toString();
                passwordRepetida = text_Password_Repetida.getText().toString();

                if (TextUtils.isEmpty(alias)
                        || TextUtils.isEmpty(nombre)
                        || TextUtils.isEmpty(apellidos)
                        || TextUtils.isEmpty(direccion)
                        || TextUtils.isEmpty(correo)
                        || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(passwordRepetida)) {

                    Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();

                } else {

                    if (password.equals(passwordRepetida)) {

                        bbddUsuario = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
                        query = bbddUsuario.orderByChild(getString(R.string.campo_alias)).equalTo(alias);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int cont = 0;

                                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                    cont++;
                                }

                                if (cont > 0) {

                                    Toast.makeText(RegistrarUsuarioActivity.this, "Elige otro alias", Toast.LENGTH_SHORT).show();

                                } else {

                                    mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                String uid = mAuth.getCurrentUser().getUid();
                                                Usuario usuario = new Usuario(alias, nombre, apellidos, direccion, correo, uid);
                                                String clave = bbddUsuario.push().getKey();
                                                bbddUsuario.child(clave).setValue(usuario);
                                                mAuth.signOut();
                                                Toast.makeText(RegistrarUsuarioActivity.this, "Registro completo", Toast.LENGTH_SHORT).show();
                                                finish();

                                            } else {

                                                Toast.makeText(RegistrarUsuarioActivity.this, "Registro fallido" + task.getException(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Toast.makeText(RegistrarUsuarioActivity.this, "Autentificación fallida", Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {

                        Toast.makeText(RegistrarUsuarioActivity.this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();

                    }
                }
        }
    }
}
