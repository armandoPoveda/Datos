package com.example.arpoga.jarmat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipalActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boton_mostar_Favoritos, boton_Registrar_Producto, boton_Mostrar_Mis_Productos, boton_Mostrar_Todos_Prodcutos, boton_Mostrar_Prodcutos_Categoria, boton_Mostrar_Prodcutos_Usuario, boton_Cerrar_Sesion, boton_Modificar_Usuario;
    private Spinner SpinnerCategorias, SpinnerUsuarios;
    private TextView textView_Alias;
    ArrayList<String> categoriaHogar;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference bbddProductos, bbddUsuarios, bbddFavoritos;
    private String clave;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        textView_Alias = (TextView) findViewById(R.id.textView_Alias);

        boton_Cerrar_Sesion = (Button) findViewById(R.id.button_Cerrar_Sesion);
        boton_Cerrar_Sesion.setOnClickListener(this);
        boton_Registrar_Producto = (Button) findViewById(R.id.button_Registrar_Producto);
        boton_Registrar_Producto.setOnClickListener(this);
        boton_Mostrar_Mis_Productos = (Button) findViewById(R.id.button_Mostrar_Producto);
        boton_Mostrar_Mis_Productos.setOnClickListener(this);
        boton_Mostrar_Prodcutos_Categoria = (Button) findViewById(R.id.button_Mostrar_Producto_Categoria);
        boton_Mostrar_Prodcutos_Categoria.setOnClickListener(this);
        boton_Mostrar_Prodcutos_Usuario = (Button) findViewById(R.id.button_Mostrar_Producto_Usuario);
        boton_Mostrar_Prodcutos_Usuario.setOnClickListener(this);
        boton_Modificar_Usuario = (Button) findViewById(R.id.button_Modificar_Usuario);
        boton_Modificar_Usuario.setOnClickListener(this);
        boton_Mostrar_Todos_Prodcutos = findViewById(R.id.button_Mostrar_Todos_Producto);
        boton_Mostrar_Todos_Prodcutos.setOnClickListener(this);
        boton_mostar_Favoritos = findViewById(R.id.button_Mostrar_Favoritos);
        boton_mostar_Favoritos.setOnClickListener(this);


        SpinnerCategorias = (Spinner) findViewById(R.id.spinner_Categorias);
        SpinnerUsuarios = (Spinner) findViewById(R.id.spinner_Usuarios);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        bbddProductos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));
        bbddUsuarios = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));
        bbddFavoritos = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_favoritos));



        RellenarSpinnerUsuarios();
        SpinnerSeleccionadoUsuarios();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_Registrar_Producto:
                startActivity(new Intent(MenuPrincipalActivity.this, RegistrarProductoActivity.class));
                break;

            case R.id.button_Mostrar_Producto:
                startActivity(new Intent(MenuPrincipalActivity.this, MostrarProductoActivity.class));
                break;

            case R.id.button_Mostrar_Todos_Producto:
                MostrarTodosProductos();

            case R.id.button_Mostrar_Producto_Categoria:
                MostrarPorCategorias();
                break;

            case R.id.button_Mostrar_Producto_Usuario:
                MostrarProductoUsuario();
                break;

            case R.id.button_Modificar_Usuario:
                ModificarUsuario();
                break;

            case R.id.button_Cerrar_Sesion:
                mAuth.signOut();
                finish();
                break;

            case R.id.button_Mostrar_Favoritos:
                startActivity(new Intent(MenuPrincipalActivity.this, MostrarProductoActivity.class));
                break;
        }
    }


    private void ModificarUsuario() {

        final String uid = mAuth.getCurrentUser().getUid();

        query = bbddUsuarios.orderByChild(getString(R.string.campo_uid)).equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

             String uidUsuario = "";
             String clave;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    clave = datasnapshot.getKey();
                     Usuario usuario = datasnapshot.getValue(Usuario.class);
                     uidUsuario = usuario.getUid();

                }

                Intent i = new Intent(MenuPrincipalActivity.this, ModificarUsuarioActivity.class);
                i.putExtra("clave", clave);
                startActivity(i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void MostrarProductoUsuario() {

        String alias = SpinnerUsuarios.getSelectedItem().toString();
        query = bbddProductos.orderByChild(getString(R.string.campo_alias)).equalTo(alias);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<String> arrayProductoUsuario = new ArrayList<>();

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot datasnapshop: dataSnapshot.getChildren()) {

                                Producto producto = datasnapshop.getValue(Producto.class);
                                String productoUsuario = producto.getNombre();
                                arrayProductoUsuario.add(productoUsuario);

                            }
                            Intent i = new Intent(MenuPrincipalActivity.this, MostrarTodosProductosActivity.class);
                            i.putStringArrayListExtra("Nombre", arrayProductoUsuario);
                            startActivity(i);
                    }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    private void SpinnerSeleccionadoUsuarios() {

        SpinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           String SpinerAlias;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                SpinerAlias = SpinnerUsuarios.getSelectedItem().toString();
               // Toast.makeText(MenuPrincipalActivity.this, "Spinner " + SpinerAlias, Toast.LENGTH_SHORT).show();
                textView_Alias.setText(SpinerAlias);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    private void RellenarSpinnerUsuarios() {

        query = bbddUsuarios.orderByChild(getString(R.string.nodo_usuarios));
        final List<String> arrayProductoUsuarios = new ArrayList<String>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Usuario usuario = datasnapshot.getValue(Usuario.class);
                    String alias = usuario.getAlias();
                    arrayProductoUsuarios.add(alias);
                }

                ArrayAdapter<String> AdapterProductoUsuarios = new ArrayAdapter<String>(MenuPrincipalActivity.this, android.R.layout.simple_spinner_item, arrayProductoUsuarios);
                AdapterProductoUsuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerUsuarios.setAdapter(AdapterProductoUsuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void MostrarPorCategorias() {

        final String SpinnerCategorias = this.SpinnerCategorias.getSelectedItem().toString();
        query = bbddProductos.orderByChild(getString(R.string.nodo_productos));
        categoriaHogar = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (SpinnerCategorias.equals("Hogar")) {

                    for (DataSnapshot datasanpshot : dataSnapshot.getChildren()) {

                        Producto producto = datasanpshot.getValue(Producto.class);
                        String productoHogar = producto.getCategoria();

                        if (productoHogar.equals("Hogar")) {
                            String nombreHogar = producto.getNombre();
                            categoriaHogar.add(nombreHogar);
                        }
                    }
                }

                if (SpinnerCategorias.equals("Electronica")) {
                    for (DataSnapshot datasanpshot : dataSnapshot.getChildren()) {

                        Producto producto = datasanpshot.getValue(Producto.class);
                        String productoElectronica = producto.getCategoria();

                        if (productoElectronica.equals("Electronica")) {
                            String nombreElectronica = producto.getNombre();
                            categoriaHogar.add(nombreElectronica);
                        }
                    }
                }

                if (SpinnerCategorias.equals("Coches")) {
                    for (DataSnapshot datasanpshot : dataSnapshot.getChildren()) {

                        Producto producto = datasanpshot.getValue(Producto.class);
                        String productoCoches = producto.getCategoria();

                        if (productoCoches.equals("Coches")) {
                            String nombreCoches = producto.getNombre();
                            categoriaHogar.add(nombreCoches);
                        }
                    }
                }
                Intent i = new Intent(MenuPrincipalActivity.this, MostrarTodosProductosActivity.class);
                i.putStringArrayListExtra("Nombre", categoriaHogar);
                startActivity(i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void MostrarTodosProductos(){
        bbddProductos.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList arrayproductosTodos = new ArrayList();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                    Producto producto = datasnapshot.getValue(Producto.class);
                    String productoNombre = producto.getNombre();
                    arrayproductosTodos.add(productoNombre);

                }

                Intent i = new Intent(MenuPrincipalActivity.this, MostrarTodosProductosActivity.class);
                i.putStringArrayListExtra("Nombre", arrayproductosTodos);
                startActivity(i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
