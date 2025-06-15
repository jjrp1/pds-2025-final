package com.pds.plugins;

import com.pds.core.Pregunta;
import com.pds.core.PluginPregunta;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PluginPreguntaCompletarHuecos implements PluginPregunta {
    @Override
    public String getTipo() {
        return "completar_huecos";
    }
    
    @Override
    public Pregunta crearPregunta(Long id, String enunciado, Object datos) {
        if (!(datos instanceof Map)) {
            throw new IllegalArgumentException("Los datos deben ser un Map");
        }
        
        Map<String, Object> datosPregunta = (Map<String, Object>) datos;
        List<String> huecos = (List<String>) datosPregunta.get("huecos");
        Map<String, String> respuestas = (Map<String, String>) datosPregunta.get("respuestas");
        
        if (huecos == null || respuestas == null) {
            throw new IllegalArgumentException("Faltan datos requeridos: huecos o respuestas");
        }
        
        // Convertir el mapa de respuestas de String a Integer
        Map<Integer, String> respuestasCorrectas = new HashMap<>();
        for (Map.Entry<String, String> entrada : respuestas.entrySet()) {
            try {
                int indice = Integer.parseInt(entrada.getKey());
                respuestasCorrectas.put(indice, entrada.getValue());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Índice de respuesta no numérico: " + entrada.getKey());
            }
        }
        
        return new PreguntaCompletarHuecos(id, enunciado, huecos, respuestasCorrectas);
    }
    
    @Override
    public boolean validarDatos(Object datos) {
        if (!(datos instanceof Map)) {
            return false;
        }
        
        Map<String, Object> datosPregunta = (Map<String, Object>) datos;
        List<String> huecos = (List<String>) datosPregunta.get("huecos");
        Map<String, String> respuestas = (Map<String, String>) datosPregunta.get("respuestas");
        
        if (huecos == null || respuestas == null || huecos.isEmpty() || respuestas.isEmpty()) {
            return false;
        }
        
        // Verificar que todos los índices de respuestas son válidos
        for (String indice : respuestas.keySet()) {
            try {
                int numIndice = Integer.parseInt(indice);
                if (numIndice < 0 || numIndice >= huecos.size()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        return true;
    }
} 