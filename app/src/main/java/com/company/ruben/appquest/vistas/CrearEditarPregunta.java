package com.company.ruben.appquest.vistas;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.company.ruben.appquest.R;
import com.company.ruben.appquest.modelo.Constants;
import com.company.ruben.appquest.modelo.MyLog;
import com.company.ruben.appquest.modelo.Pregunta;
import com.company.ruben.appquest.modelo.PreguntasRepositorio;

import java.util.ArrayList;

import static com.company.ruben.appquest.modelo.Constants.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;

/**
 * Clase que implementa la parte logica de la vista crear editar pregunta
 */

public class CrearEditarPregunta extends AppCompatActivity {

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
    private ConstraintLayout constraintLayoutCrear_pregunta;
    private ArrayList<String> categoria = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private int idPregunta;
    private String confirmar = "Confirmar";
    private String cancelar = "Cancelar";
    private Button btSpinner;

    /**
     * Estado onCreate en el cual se implementa la comprobación de campos rellenados o sin rellenar
     * por el usuario, se solicita los permisos, se guarda o edita en la base de datos
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


                    int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    MyLog.d("CrearEditarPregunta", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

                    if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Permiso denegado
                        // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
                        // En las versiones anteriores no es posible hacerlo
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(CrearEditarPregunta.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                            // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                            // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
                        } else {
                            Snackbar.make(v, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } else {
                        // Permiso aceptado
                        Snackbar.make(v, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                                .show();

                        if (idPregunta > -1) {
                            Pregunta pregunta = new Pregunta(etEnunciado.getText().toString(), spinner.getSelectedItem().toString(), etRespuestaIncorrecta1.getText()
                                    .toString(), etRespuestaIncorrecta2.getText().toString(), etRespuestaIncorrecta3.getText().toString(), etRespuestaCorrecta.getText().toString());
                            pregunta.setId(idPregunta);
                            PreguntasRepositorio.actualizar(pregunta, myContext);
                        }else {
                            Pregunta pregunta = new Pregunta(etEnunciado.getText().toString(), spinner.getSelectedItem().toString(), etRespuestaIncorrecta1.getText()
                                    .toString(), etRespuestaIncorrecta2.getText().toString(), etRespuestaIncorrecta3.getText().toString(), etRespuestaCorrecta.getText().toString());
                            PreguntasRepositorio.insertar(pregunta, myContext);
                        }

                        finish();
                    }
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




         }

    }
