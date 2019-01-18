package com.company.ruben.appquest.vistas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.company.ruben.appquest.R;
import com.company.ruben.appquest.modelo.Constants;
import com.company.ruben.appquest.modelo.MyLog;
import com.company.ruben.appquest.modelo.Pregunta;
import com.company.ruben.appquest.modelo.PreguntasRepositorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.company.ruben.appquest.modelo.Constants.CODE_CAMERA_PERMISSION;
import static com.company.ruben.appquest.modelo.Constants.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;

/**
 * Clase que implementa la parte logica de la vista crear editar pregunta
 */

public class CrearEditarPregunta extends AppCompatActivity {


    private static final int REQUEST_CAPTURE_IMAGE = 200;
    private static final int REQUEST_SELECT_IMAGE = 201;


    /**
     * Declaracion de atributos
     */

    private ConstraintLayout constraintLayout;
    private PreguntasRepositorio r = PreguntasRepositorio.getInstance();
    private EditText etRespuestaIncorrecta1;
    private Spinner spinner;
    private EditText etRespuestaIncorrecta2;
    private EditText etRespuestaIncorrecta3;
    private EditText etRespuestaCorrecta;
    private TextView etEnunciado;
    private Button button;
    private Context myContext;
    private ArrayList<String> categoria = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private int idPregunta;
    private String confirmar = "Confirmar";
    private String cancelar = "Cancelar";
    private Button btSpinner;
    private Button btCapturaFoto;
    private Button btGaleria;
    final String pathFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/demoAndroid/";
    private Uri uri;

    /**
     * Estado onCreate en el cual se implementa la comprobación de campos rellenados o sin rellenar
     * por el usuario, se solicita los permisos, se guarda o edita en la base de datos
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_pregunta);

        // Almacenamos el contexto de la actividad para utilizar en las clases internas
        myContext = this;

        // Recuperamos el Layout donde mostrar el Snackbar con las notificaciones
        constraintLayout = findViewById(R.id.constraintLayout);


        try {
            idPregunta = (int) getIntent().getExtras().get(Constants.id);
        } catch (NullPointerException ex) {
            idPregunta = -1;
        }

        try {
            categoria = r.getCategorias(myContext);
        } catch (NullPointerException ex) {
            categoria.add(Constants.categoriaVacia);
        }


        spinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(myContext, android.R.layout.simple_spinner_item, categoria);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        btCapturaFoto = findViewById(R.id.btCapturaFoto);
        btGaleria = findViewById(R.id.btGaleria);
        button = findViewById(R.id.btGuardar);
        btSpinner = findViewById(R.id.btSpinner);
        etRespuestaIncorrecta1 = findViewById(R.id.etRespuestaIncorrecta1);
        etRespuestaIncorrecta2 = findViewById(R.id.etRespuestaIncorrecta2);
        etRespuestaIncorrecta3 = findViewById(R.id.etRespuestaIncorrecta3);
        etRespuestaCorrecta = findViewById(R.id.etRespuestaCorrecta);
        etEnunciado = findViewById(R.id.etEnunciado);

        if (idPregunta > 0) {
            Pregunta p = r.getPregunta(idPregunta, myContext);

            etEnunciado.setText(p.getEnunciado());
            etRespuestaIncorrecta1.setText(p.getRespuestaIncorrecta1());
            etRespuestaIncorrecta2.setText(p.getRespuestaIncorrecta2());
            etRespuestaIncorrecta3.setText(p.getRespuestaIncorrecta3());
            etRespuestaCorrecta.setText(p.getRespuestaCorrecta());
            spinner.setSelection(categoria.indexOf(p.getCategoria()));
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (spinner.getSelectedItem().toString() == null) {
                    Snackbar.make(v, Constants.categoriaVacia, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();

                } else if (etEnunciado.getText().toString().isEmpty()) {
                    Snackbar.make(v, Constants.camposVacios, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();

                } else if (etRespuestaIncorrecta1.getText().toString().isEmpty()) {
                    Snackbar.make(v, Constants.camposVacios, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();

                } else if (etRespuestaIncorrecta2.getText().toString().isEmpty()) {
                    Snackbar.make(v, Constants.camposVacios, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();

                } else if (etRespuestaIncorrecta3.getText().toString().isEmpty()) {
                    Snackbar.make(v, Constants.camposVacios, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();

                } else if (etRespuestaCorrecta.getText().toString().isEmpty()) {
                    Snackbar.make(v, Constants.camposVacios, Snackbar.LENGTH_LONG)
                            .setAction(Constants.Action, null).show();
                } else {
                    myContext = CrearEditarPregunta.this;
                    constraintLayout = findViewById(R.id.constraintLayout);


                    //compruebaPermisosEscritura();

                        if (idPregunta > -1) {
                            Pregunta pregunta = new Pregunta(etEnunciado.getText().toString(), spinner.getSelectedItem().toString(), etRespuestaIncorrecta1.getText()
                                    .toString(), etRespuestaIncorrecta2.getText().toString(), etRespuestaIncorrecta3.getText().toString(), etRespuestaCorrecta.getText().toString());
                            pregunta.setId(idPregunta);
                            PreguntasRepositorio.actualizar(pregunta, myContext);
                        } else {
                            Pregunta pregunta = new Pregunta(etEnunciado.getText().toString(), spinner.getSelectedItem().toString(), etRespuestaIncorrecta1.getText()
                                    .toString(), etRespuestaIncorrecta2.getText().toString(), etRespuestaIncorrecta3.getText().toString(), etRespuestaCorrecta.getText().toString());
                            PreguntasRepositorio.insertar(pregunta, myContext);
                        }

                        finish();
                    }
                }
            });
        btSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Texto categoria");

                final EditText input = new EditText(myContext);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton(confirmar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerAdapter.add(input.getText().toString());
                        spinner.setSelection(spinnerAdapter.getPosition(input.getText().toString()));
                    }
                });

                builder.setNegativeButton(cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

        });


        btCapturaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compruebaPermisosEscritura();
                compruebaPermisosCamara();

            }
        });


        btGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.imageView2);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;
                case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.imageView2);
                        imageView.setImageBitmap(bmp);
                    }
                }


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    MyLog.e("Permisos: ", "Rechazados");

                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }




    // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
    // En las versiones anteriores no es posible hacerlo
    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
    private void compruebaPermisosEscritura() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(CrearEditarPregunta.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

            } else {

                MyLog.e("Permisos: ","Rechazados");

            }
        } else {

            MyLog.e("Permisos: ","Rechazados");
        }


           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(CrearEditarPregunta.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

            } else {
                Snackbar.make(constraintLayout, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(constraintLayout, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();
        }*/
    }

    // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
    // En las versiones anteriores no es posible hacerlo
    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
    private void compruebaPermisosCamara() {
        int camara = ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA);
        MyLog.d("MainActivity", "CAMERA Permission: " + camara);

        if (camara != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(CrearEditarPregunta.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_PERMISSION);

            } else {
                Snackbar.make(constraintLayout, getResources().getString(R.string.camera_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(constraintLayout, getResources().getString(R.string.camera_permission_accepted), Snackbar.LENGTH_LONG)
                    .show();
            takePicture();
        }
    }


    private void takePicture() {
        try {
            // Se crea el directorio para las fotografías
            File dirFotos = new File(pathFotos);
            dirFotos.mkdirs();

            // Se crea el archivo para almacenar la fotografía
            File fileFoto = File.createTempFile(getFileCode(),".jpg", dirFotos);

            // Se crea el objeto Uri a partir del archivo
            // A partir de la API 24 se debe utilizar FileProvider para proteger
            // con permisos los archivos creados
            // Con estas funciones podemos evitarlo
            // https://stackoverflow.com/questions/42251634/android-os-fileuriexposedexception-file-jpg-exposed-beyond-app-through-clipdata
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            uri = Uri.fromFile(fileFoto);
            Log.d("foto hecha", uri.getPath().toString());

            // Se crea la comunicación con la cámara
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Se le indica dónde almacenar la fotografía
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // Se lanza la cámara y se espera su resultado
            startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);

        } catch (IOException ex) {

            Log.d("foto no hecha", "Error: " + ex);
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
            Snackbar snackbar = Snackbar
                    .make(constraintLayout, getResources().getString(R.string.error_files), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    private String getFileCode() {
        // Se crea un código a partir de la fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());
        // Se devuelve el código
        return "pic_" + date;
    }

}