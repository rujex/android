package com.company.ruben.appquest.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.ruben.appquest.adaptadores.PreguntaSQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Repositorio donde se controla la base de datos con sus respectivos metodos
 */

public class PreguntasRepositorio {

    private static final PreguntasRepositorio instance = new PreguntasRepositorio();
    public static PreguntasRepositorio getInstance() {
        return instance;
    }
    private ArrayList<Pregunta> items = new ArrayList<>();
    private HashSet<String> listaCategorias;

    /**
     * Metodo para insertar datos en la base de datos
     * @param pregunta objeto de la clase pregunta
     * @param miContexto contexto de la actividad
     */

    public static boolean insertar(Pregunta pregunta, Context miContexto) {
        // Insertar preguntas en la base de datos

        boolean valor = true;

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);
        SQLiteDatabase db = uadh.getWritableDatabase();

        ContentValues registro = new ContentValues();

        if (db != null) {
            registro.put("enunciado", pregunta.getEnunciado().toString());
            registro.put("categoria", pregunta.getCategoria().toString());
            registro.put("respuestacorrecta", pregunta.getRespuestaCorrecta().toString());
            registro.put("respuestaincorrecta1", pregunta.getRespuestaIncorrecta1().toString());
            registro.put("respuestaincorrecta2", pregunta.getRespuestaIncorrecta2().toString());
            registro.put("respuestaincorrecta3", pregunta.getRespuestaIncorrecta3().toString());

            String[] listaColumnas = {"enunciado", "categoria", "respuestacorrecta", "respuestaincorrecta1", "respuestaincorrecta2", "respuestaincorrecta3"};

            db.insert("preguntas", "id", registro);

            db.close();
        }else{
            valor = false;
        }

        return valor;

    }


    /**
     * Actualiza un objeto de la base de datos
     *
     * @param pregunta  objeto pregunta
     * @param myContext Contexto de la actividad
     */

    public static boolean actualizar(Pregunta pregunta, Context myContext) {

        boolean valor=true;

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(myContext, Constants.basededatos, null, 1);
        SQLiteDatabase db = uadh.getWritableDatabase();

        if (db != null) {
            ContentValues valores = new ContentValues();
            // utilizamos un contenido de clave/valor para indicar las columnas y su valor asociado
            valores.put(Constants.id, pregunta.getId());
            valores.put(Constants.enunciado, pregunta.getEnunciado());
            valores.put(Constants.categoria, pregunta.getCategoria());
            valores.put(Constants.respuestaIncorrecta1, pregunta.getRespuestaIncorrecta1());
            valores.put(Constants.respuestaIncorrecta2, pregunta.getRespuestaIncorrecta2());
            valores.put(Constants.respuestaIncorrecta3, pregunta.getRespuestaIncorrecta3());
            valores.put(Constants.respuestaCorrecta, pregunta.getRespuestaCorrecta());
            // ponemos argumentos para la clausula WHERE, seran introducidos por orden pero solo uno
            // en cada simbolo ?
            String[] args = new String[]{Integer.toString(pregunta.getId())};
            db.update("preguntas", valores, "id=?", args);

            //Cerramos la base de datos
            db.close();
        }else{
            valor=false;
        }
        return valor;
    }

    /**
     * Metodo para eliminar un dato de la base de datos
     * @param id clave primaria de la base de datos
     * @param miContexto contexto de la actividad
     */
    public void eliminar(int id, Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);
        SQLiteDatabase db = uadh.getWritableDatabase();

        if(db!=null){
            db.execSQL("delete from preguntas where id='" + (id) + "'");
            db.close();
        }


    }

    /**
     * Devuelve un objeto de la base de datos por su clave primaria
     *
     * @param id  clave primaria de la base de datos
     * @param miContexto Contexto de la actividad
     * @return Pregunta devuelve un objeto pregunta
     */
    public Pregunta getPregunta( int id, Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);
        SQLiteDatabase db = uadh.getWritableDatabase();

        // Ejecutamos una sentencia sql SELECT, que devuelve un cursor con toda la informacion de la
        // base de datos que hemos indicado en la sentencia sql.
        Cursor cursor = db.rawQuery("SELECT * FROM preguntas WHERE id='"+id+"'", null);

        Pregunta p = null;


        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String enunciado = cursor.getString(cursor.getColumnIndex(Constants.enunciado));
                String categoria = cursor.getString(cursor.getColumnIndex(Constants.categoria));
                String respuestaCorrecta = cursor.getString(cursor.getColumnIndex(Constants.respuestaCorrecta));
                String respuestaIncorrecta1 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta1));
                String respuestaIncorrecta2 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta2));
                String respuestaIncorrecta3 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta3));
                p = new Pregunta(enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3);
                items.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return p;
    }

    /**
     * Devuelve todas las preguntas de la base de datos
     * @param miContexto contexto de la actividad correspondiente
     * @return preguntas
     */
    public ArrayList<Pregunta> getPreguntas(Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);
        SQLiteDatabase db = uadh.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM preguntas", null);

       items.clear();

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(Constants.id));
                String enunciado = cursor.getString(cursor.getColumnIndex(Constants.enunciado));
                String categoria = cursor.getString(cursor.getColumnIndex(Constants.categoria));
                String respuestaIncorrecta1 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta1));
                String respuestaIncorrecta2 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta2));
                String respuestaIncorrecta3 = cursor.getString(cursor.getColumnIndex(Constants.respuestaIncorrecta3));
                String respuestaCorrecta = cursor.getString(cursor.getColumnIndex(Constants.respuestaCorrecta));
                Pregunta p = new Pregunta(id, enunciado, categoria, respuestaIncorrecta1,respuestaIncorrecta2, respuestaIncorrecta3, respuestaCorrecta);
                items.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    /**
     *  Obtiene todas las categorias de la base de datos
     * @param miContexto contexto de la actividad
     * @return devuelve categorias
     */
    public ArrayList<String> getCategorias(Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);

        SQLiteDatabase db = uadh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT categoria FROM preguntas;", null);

        listaCategorias = new HashSet<>();

        if (cursor.moveToFirst()) {

            do {
                String categoria = cursor.getString(0);
                listaCategorias.add(categoria);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayList<String> categorias = new ArrayList<>(listaCategorias);
        Collections.sort(categorias);

        return categorias;
    }

    /**
     *  Devuelve el numero de categorias insertadas en la base de datos
     * @param miContexto contexto de la actividad
     * @return cantidad de categorias
     */

    public String getCantidadCategorias(Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);

        SQLiteDatabase db = uadh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(distinct categoria) FROM preguntas;", null);

        String cantidadCategorias = "";

        if (cursor.moveToFirst()) {
            cantidadCategorias = cursor.getString(0);
        }
        cursor.close();


        return cantidadCategorias;
    }

    /**
     * Metodo que muestra el numero de preguntas existentes en la base de datos
     * @param miContexto contexto de la actividad
     * @return devuelve la cantidad de preguntas
     */

    public String getCantidadPreguntas(Context miContexto) {

        PreguntaSQLiteHelper uadh = new PreguntaSQLiteHelper(miContexto, Constants.basededatos, null, 1);

        SQLiteDatabase db = uadh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(distinct id) FROM preguntas;", null);

        String cantidadPreguntas = "";

        if (cursor.moveToFirst()) {
            cantidadPreguntas = cursor.getString(0);
        }
        cursor.close();

        return cantidadPreguntas;
    }


}
