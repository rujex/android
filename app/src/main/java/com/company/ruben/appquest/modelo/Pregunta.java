package com.company.ruben.appquest.modelo;

/**
 * Clase pregunta con sus respectivos atributos, getters y setters
 */

public class Pregunta {

    /**
     * Declaración de atributos
     */

    private int id;
    private String enunciado;
    private String categoria;
    private String respuestaCorrecta;
    private String respuestaIncorrecta1;
    private String respuestaIncorrecta2;
    private String respuestaIncorrecta3;

    /**
     * Constructor de la clase con los siguientes parametros:
     * @param id
     * @param enunciado
     * @param categoria
     * @param respuestaCorrecta
     * @param respuestaIncorrecta1
     * @param respuestaIncorrecta2
     * @param respuestaIncorrecta3
     */

    public Pregunta(int id, String enunciado, String categoria, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3) {
        this.id = id;
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestaIncorrecta1 = respuestaIncorrecta1;
        this.respuestaIncorrecta2 = respuestaIncorrecta2;
        this.respuestaIncorrecta3 = respuestaIncorrecta3;
    }

    /**
     * Constructor sin el atributo id con los siguientes parametros:
     * @param enunciado
     * @param categoria
     * @param respuestaCorrecta
     * @param respuestaIncorrecta1
     * @param respuestaIncorrecta2
     * @param respuestaIncorrecta3
     */

    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestaIncorrecta1 = respuestaIncorrecta1;
        this.respuestaIncorrecta2 = respuestaIncorrecta2;
        this.respuestaIncorrecta3 = respuestaIncorrecta3;
    }

    /**
     * Getters and setters
     * @return devuelven o asignan el valor de los atributos
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRespuestaIncorrecta1() {
        return respuestaIncorrecta1;
    }

    public void setRespuestaIncorrecta1(String respuestaIncorrecta1) {
        this.respuestaIncorrecta1 = respuestaIncorrecta1;
    }

    public String getRespuestaIncorrecta2() {
        return respuestaIncorrecta2;
    }

    public void setRespuestaIncorrecta2(String respuestaIncorrecta2) {
        this.respuestaIncorrecta2 = respuestaIncorrecta2;
    }

    public String getRespuestaIncorrecta3() {
        return respuestaIncorrecta3;
    }

    public void setRespuestaIncorrecta3(String respuestaIncorrecta3) {
        this.respuestaIncorrecta3 = respuestaIncorrecta3;
    }

    /**
     * Devuelve los datos a traves del metodo toString
     * @return devuelve los datos de pregunta
     */

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", categoria='" + categoria + '\'' +
                ", respuestaCorrecta='" + respuestaCorrecta + '\'' +
                ", respuestaIncorrecta1='" + respuestaIncorrecta1 + '\'' +
                ", respuestaIncorrecta2='" + respuestaIncorrecta2 + '\'' +
                ", respuestaIncorrecta3='" + respuestaIncorrecta3 + '\'' +
                '}';
    }
}
