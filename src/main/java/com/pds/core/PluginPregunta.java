package com.pds.core;

/**
 * Interfaz que deben implementar todos los plugins de tipos de preguntas.
 */
public interface PluginPregunta {
    /**
     * Obtiene el identificador único del tipo de pregunta
     */
    String getTipo();
    
    /**
     * Crea una nueva pregunta del tipo específico a partir de los datos proporcionados
     */
    Pregunta crearPregunta(Long id, String enunciado, Object datos);
    
    /**
     * Verifica si los datos proporcionados son válidos para crear una pregunta de este tipo
     */
    boolean validarDatos(Object datos);
} 