package com.pds.modelo;

import java.util.List;
import java.util.ArrayList;

public class Bloque {
    private String id;
    private String titulo;
    private String tipo;
    private List<PreguntaBase> preguntas;
    
    public Bloque() {
        this.preguntas = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public List<PreguntaBase> getPreguntas() {
        return new ArrayList<>(preguntas);
    }
    
    public void setPreguntas(List<PreguntaBase> preguntas) {
        this.preguntas = new ArrayList<>(preguntas);
    }
} 