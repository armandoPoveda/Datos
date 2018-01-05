package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boton_Registrar_Producto, boton_Mostrar_Mis_Productos, boton_Mostrar_Todos_Productos, boton_Cerrar_Sesion;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        boton_Cerrar_Sesion = (Button) findViewById(R.id.button_Cerrar_Sesion);
        boton_Cerrar_Sesion.setOnClickListener(this);
        boton_Registrar_Producto = (Button) findViewById(R.id.button_Registrar_Producto);
        boton_Registrar_Producto.setOnClickListener(this);
        boton_Mostrar_Mis_Productos = (Button) findViewById(R.id.button_Mostrar_Producto);
        boton_Mostrar_Mis_Productos.setOnClickListener(this);
        boton_Mostrar_Todos_Productos = (Button) findViewById(R.id.button_Mostrar_Todos_Producto);
        boton_Mostrar_Todos_Productos.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button_Registrar_Producto:
                startActivity(new Intent(MenuPrincipalActivity.this, RegistrarProductoActivity.class));
                break;

            case R.id.button_Mostrar_Producto:
                startActivity(new Intent(MenuPrincipalActivity.this, MostrarProductoActivity.class));
                break;

            case R.id.button_Mostrar_Todos_Producto:
                startActivity(new Intent(MenuPrincipalActivity.this, MostrarTodosProductosActivity.class));
                break;

            case R.id.button_Cerrar_Sesion:
                mAuth.signOut();
                finish();
                break;
        }
    }
}
