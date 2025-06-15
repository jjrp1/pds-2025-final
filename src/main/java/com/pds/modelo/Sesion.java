package com.pds.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sesiones")
public class Sesion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombreCurso;
    
    @Column(nullable = false)
    private String estrategia;
    
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column
    private LocalDateTime fechaFin;
    
    @Column(nullable = false)
    private int preguntaActual;
    
    @Column(nullable = false)
    private int totalPreguntas;
    
    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();
    
    @Column(nullable = false)
    private boolean completada;
    
    // Constructor por defecto requerido por JPA
    public Sesion() {}
    
    // Constructor para nueva sesión
    public Sesion(Curso curso, String estrategia) {
        this.nombreCurso = curso.getNombre();
        this.estrategia = estrategia;
        this.fechaInicio = LocalDateTime.now();
        this.preguntaActual = 0;
        this.totalPreguntas = curso.getBloques().get(0).getPreguntas().size();
        this.completada = false;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public String getNombreCurso() {
        return nombreCurso;
    }
    
    public String getEstrategia() {
        return estrategia;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public int getPreguntaActual() {
        return preguntaActual;
    }
    
    public void setPreguntaActual(int preguntaActual) {
        this.preguntaActual = preguntaActual;
    }
    
    public int getTotalPreguntas() {
        return totalPreguntas;
    }
    
    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
    
    public void addRespuesta(Respuesta respuesta) {
        respuestas.add(respuesta);
        respuesta.setSesion(this);
    }
    
    public boolean isCompletada() {
        return completada;
    }
    
    public void setCompletada(boolean completada) {
        this.completada = completada;
        if (completada) {
            this.fechaFin = LocalDateTime.now();
        }
    }
} 