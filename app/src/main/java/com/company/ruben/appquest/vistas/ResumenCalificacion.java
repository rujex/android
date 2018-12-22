package com.company.ruben.appquest.vistas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.company.ruben.appquest.R;
import com.company.ruben.appquest.modelo.MyLog;
import com.company.ruben.appquest.modelo.PreguntasRepositorio;

/**
 * Clase que muestra un resumen con la cantidad de preguntas y categorias
 */

public class ResumenCalificacion extends AppCompatActivity {

    private PreguntasRepositorio r = PreguntasRepositorio.getInstance();
    private static final String TAG="ResumenActivity";
    private Context miContexto = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resumen_calificacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Asignar la acción necesaria. En este caso "volver atrás"
                    onBackPressed();

                }
            });
        } else {
            MyLog.d("SobreNosotros", "Error al cargar toolbar");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_acerca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acerca) {
            Intent intent = new Intent(ResumenCalificacion.this, AcercaDe.class);
            ResumenCalificacion.this.startActivity(intent);
            return true;
        }

        if (id == R.id.action_preguntas) {
            Intent intent = new Intent(ResumenCalificacion.this, ListadoPreguntas.class);
            ResumenCalificacion.this.startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    /**
     * Asigna los valores a las preguntas disponibles y categorias
     */

    @Override
    protected void onStart() {
        super.onStart();
        //La actividad está a punto de hacerse visible.


        MyLog.d(TAG, "Finalizando OnStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView preguntasDisponibles = findViewById(R.id.preguntasDisponibles);
        TextView categoriasDisponibles = findViewById(R.id.categoriasDisponibles);
        preguntasDisponibles.setText(r.getCantidadPreguntas(miContexto));
        categoriasDisponibles.setText(r.getCantidadCategorias(miContexto));
        //La actividad se ha hecho visible (ahora se "reanuda").
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Otra actividad está enfocando (esta actividad está a punto de ser "pausada").
    }
    @Override
    protected void onStop() {
        super.onStop();
        //La actividad ya no es visible (ahora está "detenida").
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //La actividad está a punto de ser destruida.
    }
}


