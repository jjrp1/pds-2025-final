package com.pds.plugins;

import com.pds.core.Pregunta;
import com.pds.core.PluginPregunta;
import java.util.List;
import java.util.Map;

public class PluginPreguntaTest implements PluginPregunta {
    @Override
    public String getTipo() {
        return "test";
    }
    
    @Override
    public Pregunta crearPregunta(Long id, String enunciado, Object datos) {
        if (!(datos instanceof Map)) {
            throw new IllegalArgumentException("Los datos deben ser un Map");
        }
        
        Map<String, Object> datosPregunta = (Map<String, Object>) datos;
        List<String> opciones = (List<String>) datosPregunta.get("opciones");
        Integer opcionCorrecta = (Integer) datosPregunta.get("opcionCorrecta");
        
        if (opciones == null || opcionCorrecta == null) {
            throw new IllegalArgumentException("Faltan datos requeridos: opciones o opcionCorrecta");
        }
        
        return new PreguntaTest(id, enunciado, opciones, opcionCorrecta);
    }
    
    @Override
    public boolean validarDatos(Object datos) {
        if (!(datos instanceof Map)) {
            return false;
        }
        
        Map<String, Object> datosPregunta = (Map<String, Object>) datos;
        List<String> opciones = (List<String>) datosPregunta.get("opciones");
        Integer opcionCorrecta = (Integer) datosPregunta.get("opcionCorrecta");
        
        return opciones != null && 
               !opciones.isEmpty() && 
               opcionCorrecta != null && 
               opcionCorrecta >= 0 && 
               opcionCorrecta < opciones.size();
    }
} 