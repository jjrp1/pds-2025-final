package com.pds.modelo;

public class PreguntaBase {
    private String id;
    private String tipo;
    private String enunciado;
    private Object datos;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getEnunciado() {
        return enunciado;
    }
    
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    
    public Object getDatos() {
        return datos;
    }
    
    public void setDatos(Object datos) {
        this.datos = datos;
    }
} 