package com.pds.plugins;

import com.pds.core.Pregunta;
import java.util.List;
import java.util.ArrayList;

public class PreguntaTest implements Pregunta {
    private final Long id;
    private final String enunciado;
    private final List<String> opciones;
    private final int opcionCorrecta;
    
    public PreguntaTest(Long id, String enunciado, List<String> opciones, int opcionCorrecta) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = new ArrayList<>(opciones);
        this.opcionCorrecta = opcionCorrecta;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public String getEnunciado() {
        return enunciado;
    }
    
    @Override
    public String getTipo() {
        return "test";
    }
    
    @Override
    public boolean verificarRespuesta(String respuesta) {
        try {
            int opcionSeleccionada = Integer.parseInt(respuesta);
            return opcionSeleccionada == opcionCorrecta;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public String getRespuestaCorrecta() {
        return String.valueOf(opcionCorrecta);
    }
    
    public List<String> getOpciones() {
        return new ArrayList<>(opciones);
    }
} 