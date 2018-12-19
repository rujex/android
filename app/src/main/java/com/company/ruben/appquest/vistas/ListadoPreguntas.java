package com.company.ruben.appquest.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.company.ruben.appquest.R;
import com.company.ruben.appquest.adaptadores.Pregunta_Adapter;
import com.company.ruben.appquest.modelo.Constants;
import com.company.ruben.appquest.modelo.MyLog;
import com.company.ruben.appquest.modelo.Pregunta;
import com.company.ruben.appquest.modelo.PreguntasRepositorio;

import java.util.ArrayList;

/**
 * Clase que muestra todas las preguntas almacenadas con la posibilidad de borrarlas o editarlas
 * con un swipe
 */

public class ListadoPreguntas extends AppCompatActivity {

    /**
     * Declaracion de atributos
     */

    private ArrayList<Pregunta> items = new ArrayList<>();
    private Context miContexto = this;
    private PreguntasRepositorio r = PreguntasRepositorio.getInstance();
    private String cancelar = "Cancelar";
    private String confirmar = "Confirmar";

    /**
     * Estado onCreate donde se inicia la actividad creareditarpregunta cuando presionemos en el boton
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_preguntas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListadoPreguntas.this,CrearEditarPregunta.class));
            }
        });
    }

    /**
     * metodo onStart que carga el listado con las preguntas
     */
    @Override
    protected void onStart() {
        MyLog.d(Constants.TAG, "Iniciando OnStart");
        super.onStart();
        cargarListado();

        MyLog.d(Constants.TAG, "Finalizando OnStart");
    }

    /**
     * Metodo onresume interactua mediante un swipe con las preguntas
     */

    @Override
    protected void onResume() {
        MyLog.d(Constants.TAG, "Iniciando OnResume");
        super.onResume();
        cargarListado();
        // Inicializa el RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        if (!items.isEmpty()) {
            TextView e = findViewById(R.id.textView8);
            e.setVisibility(View.INVISIBLE);

            // Crea el Adaptador con los datos de la lista anterior
            Pregunta_Adapter adaptador = new Pregunta_Adapter(items);

            // Asocia el Adaptador al RecyclerView
            recyclerView.setAdapter(adaptador);

            final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                    final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                    if (direction == ItemTouchHelper.LEFT) { //if swipe left

                        final AlertDialog.Builder builder = new AlertDialog.Builder(miContexto);

                        builder.setTitle("Eliminar Pregunta");
                        builder.setMessage("¿Estás seguro que deseas eliminar la pregunta?");

                        builder.setPositiveButton(confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                r.eliminar(items.get(position).getId(), miContexto);
                                recreate();
                            }
                        });

                        builder.setNegativeButton(cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                recreate();
                            }
                        });

                        builder.setCancelable(false);
                        builder.show();

                    }

                    if (direction == ItemTouchHelper.RIGHT) { //if swipe right

                        AlertDialog.Builder builder = new AlertDialog.Builder(miContexto);
                        builder.setTitle("Editar Pregunta");
                        builder.setMessage("¿Estás seguro de editar la pregunta?");

                        builder.setPositiveButton(confirmar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editarPregunta(items.get(position).getId());
                            }
                        });
                        builder.setNegativeButton(cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                recreate();
                            }
                        });

                        builder.setCancelable(false);
                        builder.show();
                    }
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView); //set swipe to recyclerview

            // Muestra el RecyclerView en vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyLog.d(Constants.TAG, "Finalizando OnResume");
        } else {
            TextView e = findViewById(R.id.textView8);
            e.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        MyLog.d(Constants.TAG, "Iniciando OnPause");
        super.onPause();
        MyLog.d(Constants.TAG, "Finalizando OnPause");
    }

    @Override
    protected void onStop() {
        MyLog.d(Constants.TAG, "Iniciando OnStop");
        super.onStop();
        MyLog.d(Constants.TAG, "Finalizando OnStop");
    }

    @Override
    protected void onRestart() {
        MyLog.d(Constants.TAG, "Iniciando OnRestart");
        super.onRestart();
        MyLog.d(Constants.TAG, "Finalizando OnRestart");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(Constants.TAG, "Iniciando OnDestroy");
        super.onDestroy();
        MyLog.d(Constants.TAG, "Finalizando OnDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void cargarListado() {
        items = r.getPreguntas(miContexto);

    }

    public void editarPregunta(int id) {
        Intent intent = new Intent(ListadoPreguntas.this, CrearEditarPregunta.class);
        intent.putExtra(Constants.id, id);
        startActivity(intent);
    }



}
