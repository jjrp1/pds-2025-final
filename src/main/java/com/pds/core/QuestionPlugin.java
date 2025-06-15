package com.pds.core;

/**
 * Interfaz que deben implementar todos los plugins de tipos de preguntas.
 */
public interface QuestionPlugin {
    /**
     * Obtiene el identificador único del tipo de pregunta
     */
    String getType();
    
    /**
     * Crea una nueva pregunta del tipo específico a partir de los datos proporcionados
     */
    Question createQuestion(String id, String text, Object data);
    
    /**
     * Verifica si los datos proporcionados son válidos para crear una pregunta de este tipo
     */
    boolean validateData(Object data);
} 