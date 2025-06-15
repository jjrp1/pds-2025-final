package com.pds.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = false)
    private Sesion sesion;
    
    @Column(nullable = false)
    private int numeroPregunta;
    
    @Column(nullable = false)
    private String respuestaUsuario;
    
    @Column(nullable = false)
    private String respuestaCorrecta;
    
    @Column(nullable = false)
    private boolean esCorrecta;
    
    @Column(nullable = false)
    private LocalDateTime fechaRespuesta;
    
    // Constructor por defecto requerido por JPA
    public Respuesta() {}
    
    // Constructor para nueva respuesta
    public Respuesta(int numeroPregunta, String respuestaUsuario, String respuestaCorrecta) {
        this.numeroPregunta = numeroPregunta;
        this.respuestaUsuario = respuestaUsuario;
        this.respuestaCorrecta = respuestaCorrecta;
        this.esCorrecta = respuestaUsuario.equals(respuestaCorrecta);
        this.fechaRespuesta = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public Sesion getSesion() {
        return sesion;
    }
    
    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
    
    public int getNumeroPregunta() {
        return numeroPregunta;
    }
    
    public String getRespuestaUsuario() {
        return respuestaUsuario;
    }
    
    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
    
    public boolean isEsCorrecta() {
        return esCorrecta;
    }
    
    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }
} 