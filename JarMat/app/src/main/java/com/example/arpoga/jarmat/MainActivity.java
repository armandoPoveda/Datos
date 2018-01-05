package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_Password, text_Correo;
    private Button boton_Login, boton_Registrar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_Correo = (EditText) findViewById(R.id.editText_Correo);
        text_Password = (EditText) findViewById(R.id.editText_Password);

        boton_Login = (Button) findViewById(R.id.button_Login);
        boton_Login.setOnClickListener(this);
        boton_Registrar = (Button) findViewById(R.id.button_Registrar);
        boton_Registrar.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_Registrar:

                Intent i = new Intent(MainActivity.this, RegistrarUsuarioActivity.class);
                startActivity(i);
                break;

            case R.id.button_Login:

                final String email = text_Correo.getText().toString();
                String password = text_Password.getText().toString();

                if (TextUtils.isEmpty(email)

                        || TextUtils.isEmpty(password)) {

                    Toast.makeText(MainActivity.this, "Rellenar los campos vacios", Toast.LENGTH_SHORT).show();

                } else {

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Intent i = new Intent(MainActivity.this, MenuPrincipalActivity.class);
                                startActivity(i);
                                Toast.makeText(MainActivity.this, "Bienvenido " + email, Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(MainActivity.this, "Fallo en el login", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
        }
    }
}
