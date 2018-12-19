package com.company.ruben.appquest.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.company.ruben.appquest.R;

/**
 * Clase que implementa la logica de la actividad principal donde muestra el
 * logo durante unos segundos
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Despues de unos segundos lanza la actividad resumen calificacion
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable(){
            public void run(){

                Intent intent = new Intent(MainActivity.this, ResumenCalificacion.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        }, 3000);

        setContentView(R.layout.activity_main);


            }
        ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ajuste) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
