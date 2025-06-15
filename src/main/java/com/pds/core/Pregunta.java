package com.pds.core;

/**
 * Interfaz base para todos los tipos de preguntas en el sistema.
 * Define los métodos que deben implementar todos los tipos de preguntas.
 */
public interface Pregunta {
    /**
     * Obtiene el identificador único de la pregunta
     */
    Long getId();
    
    /**
     * Obtiene el texto de la pregunta
     */
    String getEnunciado();
    
    /**
     * Obtiene el tipo de pregunta
     */
    String getTipo();
    
    /**
     * Verifica si la respuesta proporcionada es correcta
     */
    boolean verificarRespuesta(String respuesta);
    
    /**
     * Obtiene la respuesta correcta
     */
    String getRespuestaCorrecta();
} 