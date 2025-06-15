package com.pds.core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Gestor de plugins que implementa el microkernel para los tipos de preguntas.
 * Permite cargar dinámicamente nuevos tipos de preguntas.
 */
public class GestorPluginsPreguntas {
    private static GestorPluginsPreguntas instancia;
    private final Map<String, PluginPregunta> plugins;
    private final File directorioPlugins;
    
    private GestorPluginsPreguntas() {
        plugins = new HashMap<>();
        directorioPlugins = new File("plugins");
        if (!directorioPlugins.exists()) {
            directorioPlugins.mkdirs();
        }
    }
    
    public static synchronized GestorPluginsPreguntas getInstancia() {
        if (instancia == null) {
            instancia = new GestorPluginsPreguntas();
        }
        return instancia;
    }
    
    /**
     * Carga todos los plugins disponibles en el directorio de plugins
     */
    public void cargarPlugins() {
        File[] archivosJar = directorioPlugins.listFiles((dir, name) -> name.endsWith(".jar"));
        if (archivosJar == null) return;
        
        for (File archivoJar : archivosJar) {
            try {
                URL[] urls = new URL[]{archivoJar.toURI().toURL()};
                URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
                
                ServiceLoader<PluginPregunta> serviceLoader = ServiceLoader.load(PluginPregunta.class, classLoader);
                for (PluginPregunta plugin : serviceLoader) {
                    plugins.put(plugin.getTipo(), plugin);
                }
            } catch (Exception e) {
                System.err.println("Error cargando plugin: " + archivoJar.getName());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Obtiene un plugin por su tipo
     */
    public PluginPregunta obtenerPlugin(String tipo) {
        return plugins.get(tipo);
    }
    
    /**
     * Crea una pregunta del tipo especificado
     */
    public Pregunta crearPregunta(String tipo, Long id, String enunciado, Object datos) {
        PluginPregunta plugin = obtenerPlugin(tipo);
        if (plugin == null) {
            throw new IllegalArgumentException("Tipo de pregunta no soportado: " + tipo);
        }
        if (!plugin.validarDatos(datos)) {
            throw new IllegalArgumentException("Datos inválidos para el tipo de pregunta: " + tipo);
        }
        return plugin.crearPregunta(id, enunciado, datos);
    }
    
    /**
     * Verifica si un tipo de pregunta está soportado
     */
    public boolean esTipoSoportado(String tipo) {
        return plugins.containsKey(tipo);
    }
    
    /**
     * Obtiene todos los tipos de preguntas soportados
     */
    public String[] obtenerTiposSoportados() {
        return plugins.keySet().toArray(new String[0]);
    }
} 