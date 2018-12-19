package com.company.ruben.appquest.adaptadores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que implementa la base de datos a la aplicación
 */

public class PreguntaSQLiteHelper extends SQLiteOpenHelper{

    /**
     * Creación de la tabla preguntas
     */
    String sqlCreate = "CREATE TABLE preguntas (id INTEGER PRIMARY KEY AUTOINCREMENT, enunciado TEXT, categoria TEXT," +
            " respuestacorrecta TEXT, respuestaincorrecta1 TEXT," +
            " respuestaincorrecta2 TEXT, respuestaincorrecta3 TEXT)";


    /**
     * Constructor con una serie de parametros:
     * @param contexto
     * @param BaseDeDatos
     * @param factory
     * @param version
     */

    public PreguntaSQLiteHelper(Context contexto, String BaseDeDatos, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, BaseDeDatos, factory, version);
    }

    /**
     * En el metodo onCreate ejecuta la sentencia sql para crear la tabla
     * Parametro:
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    /**
     * En el metodo onUpgrade elimina la version anterior de la tabla
     * @param db
     * @param versionAnterior
     * @param versionNueva
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Preguntas");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

}
