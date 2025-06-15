package com.pds.modelo;

import java.util.List;
import java.util.ArrayList;

public class Curso {
    private String id;
    private String nombre;
    private String descripcion;
    private List<Bloque> bloques;
    
    public Curso() {
        this.bloques = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List<Bloque> getBloques() {
        return new ArrayList<>(bloques);
    }
    
    public void setBloques(List<Bloque> bloques) {
        this.bloques = new ArrayList<>(bloques);
    }
} 