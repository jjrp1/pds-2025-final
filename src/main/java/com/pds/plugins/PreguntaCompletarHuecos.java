package com.pds.plugins;

import com.pds.core.Pregunta;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class PreguntaCompletarHuecos implements Pregunta {
    private final Long id;
    private final String enunciado;
    private final List<String> huecos;
    private final Map<Integer, String> respuestasCorrectas;
    
    public PreguntaCompletarHuecos(Long id, String enunciado, List<String> huecos, Map<Integer, String> respuestasCorrectas) {
        this.id = id;
        this.enunciado = enunciado;
        this.huecos = new ArrayList<>(huecos);
        this.respuestasCorrectas = new HashMap<>(respuestasCorrectas);
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public String getEnunciado() {
        return enunciado;
    }
    
    @Override
    public String getTipo() {
        return "completar_huecos";
    }
    
    @Override
    public boolean verificarRespuesta(String respuesta) {
        try {
            // La respuesta debe ser un JSON con el formato: {"0": "respuesta1", "1": "respuesta2", ...}
            Map<Integer, String> respuestasUsuario = parsearRespuestas(respuesta);
            
            // Verificar que todas las respuestas correctas están presentes y son correctas
            for (Map.Entry<Integer, String> entrada : respuestasCorrectas.entrySet()) {
                String respuestaUsuario = respuestasUsuario.get(entrada.getKey());
                if (respuestaUsuario == null || 
                    !respuestaUsuario.trim().equalsIgnoreCase(entrada.getValue().trim())) {
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getRespuestaCorrecta() {
        // Convertir el mapa de respuestas correctas a formato JSON
        StringBuilder json = new StringBuilder("{");
        boolean primero = true;
        for (Map.Entry<Integer, String> entrada : respuestasCorrectas.entrySet()) {
            if (!primero) {
                json.append(",");
            }
            json.append("\"").append(entrada.getKey()).append("\":\"")
                .append(entrada.getValue()).append("\"");
            primero = false;
        }
        json.append("}");
        return json.toString();
    }
    
    public List<String> getHuecos() {
        return new ArrayList<>(huecos);
    }
    
    public Map<Integer, String> getRespuestasCorrectas() {
        return new HashMap<>(respuestasCorrectas);
    }
    
    private Map<Integer, String> parsearRespuestas(String respuesta) {
        // Implementación básica de parsing de JSON
        Map<Integer, String> resultado = new HashMap<>();
        respuesta = respuesta.trim();
        if (!respuesta.startsWith("{") || !respuesta.endsWith("}")) {
            throw new IllegalArgumentException("Formato de respuesta inválido");
        }
        
        String contenido = respuesta.substring(1, respuesta.length() - 1);
        String[] pares = contenido.split(",");
        
        for (String par : pares) {
            String[] partes = par.split(":");
            if (partes.length != 2) {
                throw new IllegalArgumentException("Formato de par clave-valor inválido");
            }
            
            String clave = partes[0].trim().replace("\"", "");
            String valor = partes[1].trim().replace("\"", "");
            
            try {
                int indice = Integer.parseInt(clave);
                resultado.put(indice, valor);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Clave no numérica");
            }
        }
        
        return resultado;
    }
} 