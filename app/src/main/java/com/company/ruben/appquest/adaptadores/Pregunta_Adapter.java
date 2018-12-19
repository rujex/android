package com.company.ruben.appquest.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.ruben.appquest.R;
import com.company.ruben.appquest.modelo.Pregunta;

import java.util.ArrayList;

public class Pregunta_Adapter extends RecyclerView.Adapter<Pregunta_Adapter.PreguntaViewHolder> implements View.OnClickListener{

    private ArrayList<Pregunta> preguntas;
    private View.OnClickListener listener;

    /**
     * Se implementa el viewholder que trata de almacenar
     * la vista del elemento y sus datos
     */


public static class PreguntaViewHolder extends RecyclerView.ViewHolder {

    /**
     * Declaración de variables
     */

    private TextView TextView_id;
    private TextView TextView_enunciado;
    private TextView TextView_categoria;

    /**
     *  Metodo para recoger los id de cada elemento
     * @param itemView
     */

    public PreguntaViewHolder(View itemView) {
        super(itemView);
        TextView_id = itemView.findViewById(R.id.preguntasDisponibles);
        TextView_enunciado = itemView.findViewById(R.id.tvPregunta);
        TextView_categoria = itemView.findViewById(R.id.tvCateg);
    }

    /**
     * Metodo que asigna los datos
     * @param item
     */

    public void PreguntaBind(Pregunta item) {
        TextView_id.setText(Integer.toString(item.getId()));
        TextView_enunciado.setText(item.getEnunciado());
        TextView_categoria.setText(item.getCategoria());
    }
}


    /**
     * Metodo que crea el objeto adaptador recogiendo el array con los datos de pregunta
     * @param preguntas
     */

    public Pregunta_Adapter(@NonNull ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * Se encarga de crear objetos ViewHolder necesarios
     * para los elementos de la colección
     * Parametros:
     * @param parent
     * @param viewType
     * @return devuelve el objeto ViewHolder
     */
    @Override
    public PreguntaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        row.setOnClickListener(this);

        PreguntaViewHolder avh = new PreguntaViewHolder(row);
        return avh;
    }


    /**
     * Metodo que se encarga de actualizaar los datos del viewholder existente
     * Parametros:
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(PreguntaViewHolder viewHolder, int position) {
        Pregunta item = preguntas.get(position);
        viewHolder.PreguntaBind(item);
    }


    /**
     * Metodo que devuelve el numero de elementos de la lista de datos.
     * @return devuelve el tamaño
     */
    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    /**
     * Se añade un listener al elemento
     * Parametro:
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Se inserta el metodo on click en caso de no ser nulo pasando por Parametro:
     * @param view
     */

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

}
