package com.pds.core;

/**
 * Interfaz base para todos los tipos de preguntas en el sistema.
 * Define los métodos que deben implementar todos los tipos de preguntas.
 */
public interface Question {
    /**
     * Obtiene el identificador único de la pregunta
     */
    String getId();
    
    /**
     * Obtiene el texto de la pregunta
     */
    String getText();
    
    /**
     * Obtiene el tipo de pregunta
     */
    String getType();
    
    /**
     * Verifica si la respuesta proporcionada es correcta
     */
    boolean checkAnswer(String answer);
    
    /**
     * Obtiene la respuesta correcta
     */
    String getCorrectAnswer();
} 