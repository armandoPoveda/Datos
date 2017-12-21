package com.example.arpoga.googleservices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.FileFilter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


import static com.google.android.gms.drive.Drive.getDriveResourceClient;

//en el gradle hay que añadir  compile 'com.google.android.gms:play-services-auth:11.6.0' la services que vayamos a utilizar
//también abrá que registrar la aplicación en una cuenta gmaily dar las credenciales

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //autorizamos el google drive api en nuestra aplicación con GoogleSignInClient
    private GoogleSignInClient mGoogleSignInClient;

    //un tag para el LOG.
    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_SIGN_IN = 0;

    //obejtos de tipo DriveClient
    private DriveClient mDriveClient;

    // obejto de tipo DriveResourceClient
    private DriveResourceClient mDriveResourceClient;

    //obejto de tipo Query
    private Query query;

    private Button buscar, añadir, ListarArchivos;
    private EditText NombreArchivo, NombreTitulo, Texto;

    private ListView LV;
    private ArrayAdapter Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NombreArchivo = (EditText) findViewById(R.id.editText_Buscar);
        NombreTitulo = (EditText) findViewById(R.id.editText_AñadirTitulo);
        Texto = (EditText) findViewById(R.id.editText_AñadirTexto);

        buscar = (Button) findViewById(R.id.button_Buscar);
        buscar.setOnClickListener(this);
        añadir = (Button) findViewById(R.id.button_AñadirTitulo);
        añadir.setOnClickListener(this);
        ListarArchivos = (Button) findViewById(R.id.button_ListarArchivos);
        ListarArchivos.setOnClickListener(this);

        LV = (ListView) findViewById(R.id.Lista);

        //con este metodo nos devuelve un obejeto del tipo GoogleSignInClient
        mGoogleSignInClient = buildGoogleSignInClient();

        //toast para indicar que se a conectado
        Toast.makeText(this, "Google Singnin obtenido", Toast.LENGTH_SHORT).show();

        //con este getSignInIntent llamará al startActivityForResult y procesaráel resultado
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);

    }

    //este es el metodo que nos devulve el objeto GoogleSignInClient que llama a la clase GoogleSignIn y obtiene el cliente
    // y lo devolverá en el objeto mGoogleSignInClient que hemos creado para guardarlo
    private GoogleSignInClient buildGoogleSignInClient() {

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(Drive.SCOPE_FILE).build();

        return GoogleSignIn.getClient(this, signInOptions);

    }

    //procesa el resultado del getSignInIntent en onActivityResult
    //añadimos con toas los paso que va haciendo la aplicación para loguearse
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //paso 1 obtiene el startActivityForResult
        Toast.makeText(this, "Paso 1", Toast.LENGTH_SHORT).show();

        switch (requestCode) {

            case REQUEST_CODE_SIGN_IN:

                //paso la variable statica REQUEST_CODE_SIGN_IN del startActivityForResult
                Toast.makeText(this, "Paso 2", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "Sign in request code");

                // Si el resultado es correcto
                if (resultCode == RESULT_OK) {

                    //si el result es OK se conecta
                    Toast.makeText(this, "Paso 3", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "Signed in successfully.");

                    // con cogeremos el cliente logueado y podremos acceder a los contenidos del drive
                    mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));

                    // así accedemos al contenido del drive
                    mDriveResourceClient = getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));

                    Toast.makeText(this, "Paso 4", Toast.LENGTH_SHORT).show();

                }
                break;
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_Buscar:

                //utilizamos un query para aplicar flitro en la opción de busqueda y que nos busque por el noombre que intriducimos el archivo o archivos
                query = new Query.Builder().addFilter(Filters.eq(SearchableField.TITLE, NombreArchivo.getText().toString())).build();

                //obtenemos un query del mDriveResourceClient pasandole el flitro
                final Task<MetadataBuffer> queryTask = mDriveResourceClient.query(query);

                queryTask.addOnSuccessListener(this, new OnSuccessListener<MetadataBuffer>() {
                    @Override
                    public void onSuccess(MetadataBuffer metadataBuffer) {

                        //si el queryTAsk es mayor a cero que entre dentro del if
                        if (queryTask.getResult().getCount() > 0){

                            //cogemos el nombre original y lo guardamos en un variable
                            String nombreArchivo = queryTask.getResult().get(0).getOriginalFilename();

                        //guardamos el resultado en un variable int con la cantidad de archivos que existen con el nombre introducido
                        int i = queryTask.getResult().getCount();

                        //si no que nos diga que no hay archivos con el nombre introducido
                            //si el resultado es distinto de 0 que nos diga cuantos archivos hay
                            if (nombreArchivo != null && i != 0) {

                            Toast.makeText(MainActivity.this, "El archivo existe " + nombreArchivo + " y hay " + i + " archivo/os", Toast.LENGTH_SHORT).show();
                        }
                    }
                        else {
                            Toast.makeText(MainActivity.this, "No se ha encontrado ningún archivo", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });break;

            case R.id.button_AñadirTitulo:

                //obtenemos el directorio raiz getRootFolder del objeto DriveResourceClient = guardado en mDriveResourceClient
                final Task<DriveFolder> rootFolderTask = mDriveResourceClient.getRootFolder();

                //crearemos contenido
                final Task<DriveContents> createContentsTask = mDriveResourceClient.createContents();

                Tasks.whenAll(rootFolderTask, createContentsTask).continueWithTask(new Continuation<Void, Task<DriveFile>>() {

                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {

                        //directorio raiz
                        DriveFolder parent = rootFolderTask.getResult();

                        //creamos contenido
                        DriveContents contents = createContentsTask.getResult();

                        //obejto OutputStream para crear un fichero
                        OutputStream outputStream = contents.getOutputStream();
                        try (Writer writer = new OutputStreamWriter(outputStream)) {

                            //escribimos en el archivo el texto cogido de PlainText
                            writer.write(Texto.getText().toString());
                        }
                        //con este método creamos el archivo
                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()

                                //añadiendo el titulo cogido de un PlainText
                                .setTitle(NombreTitulo.getText().toString())
                                .setMimeType("text/plain")
                                .setStarred(true)
                                .build();

                        //nos devolverá DriveResourceClient con el archivo creado
                        return mDriveResourceClient.createFile(parent, changeSet, contents);
                    }
                })
                        //si nos crea el fichero nos lanzará un toast
                        .addOnSuccessListener(this, new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {

                                Toast.makeText(MainActivity.this, "Fichero creado", Toast.LENGTH_SHORT).show();
                            }
                        })

                        //y si no ha podido crearlo otro toast
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Unable to create file", e);
                                Toast.makeText(MainActivity.this, "Fichero no creado", Toast.LENGTH_SHORT).show();
                            }
                        });break;

                        //caso para listar archivos
            case R.id.button_ListarArchivos:


                //hacemos un query pasandole como filtro el MIMI_TYPE
                query = new Query.Builder().addFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain")).build();
                //un task del query para los matadatos
                Task<MetadataBuffer> queryTaskListar = mDriveResourceClient.query(query);

                //añadimos el método para el task
                queryTaskListar.addOnSuccessListener(this, new OnSuccessListener<MetadataBuffer>() {
                    @Override
                    public void onSuccess(MetadataBuffer metadataBuffer) {

                        //un array de strings para guardar el nombre de los archivos
                        ArrayList<String> archivos = new ArrayList<>();

                        //for para recorrer los matadata y añadirlos al array
                        for(Metadata metadatabuff: metadataBuffer){

                            //guardamos en una variable el nombre original de los ficheros
                          String name =  metadatabuff.getOriginalFilename();

                          //añadimos al array
                            archivos.add(name);

                        }

                        //creamos un ArrayAdapter
                        Adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, archivos);

                        //Rellenamos el listviw con los archivos
                        LV.setAdapter(Adapter);

                    }

                })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure...
                                Log.e(TAG, "Error retrieving files", e);
                                // [START_EXCLUDE]
                                Toast.makeText(MainActivity.this,"Error obteniendo la lista",Toast.LENGTH_SHORT).show();
                            }
                        });break;
        }
    }
}

