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
public class QuestionPluginManager {
    private static QuestionPluginManager instance;
    private final Map<String, QuestionPlugin> plugins;
    private final File pluginDirectory;
    
    private QuestionPluginManager() {
        plugins = new HashMap<>();
        pluginDirectory = new File("plugins");
        if (!pluginDirectory.exists()) {
            pluginDirectory.mkdirs();
        }
    }
    
    public static synchronized QuestionPluginManager getInstance() {
        if (instance == null) {
            instance = new QuestionPluginManager();
        }
        return instance;
    }
    
    /**
     * Carga todos los plugins disponibles en el directorio de plugins
     */
    public void loadPlugins() {
        File[] jarFiles = pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null) return;
        
        for (File jarFile : jarFiles) {
            try {
                URL[] urls = new URL[]{jarFile.toURI().toURL()};
                URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
                
                ServiceLoader<QuestionPlugin> serviceLoader = ServiceLoader.load(QuestionPlugin.class, classLoader);
                for (QuestionPlugin plugin : serviceLoader) {
                    plugins.put(plugin.getType(), plugin);
                }
            } catch (Exception e) {
                System.err.println("Error cargando plugin: " + jarFile.getName());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Obtiene un plugin por su tipo
     */
    public QuestionPlugin getPlugin(String type) {
        return plugins.get(type);
    }
    
    /**
     * Crea una pregunta del tipo especificado
     */
    public Question createQuestion(String type, String id, String text, Object data) {
        QuestionPlugin plugin = getPlugin(type);
        if (plugin == null) {
            throw new IllegalArgumentException("Tipo de pregunta no soportado: " + type);
        }
        if (!plugin.validateData(data)) {
            throw new IllegalArgumentException("Datos inválidos para el tipo de pregunta: " + type);
        }
        return plugin.createQuestion(id, text, data);
    }
    
    /**
     * Verifica si un tipo de pregunta está soportado
     */
    public boolean isTypeSupported(String type) {
        return plugins.containsKey(type);
    }
    
    /**
     * Obtiene todos los tipos de preguntas soportados
     */
    public String[] getSupportedTypes() {
        return plugins.keySet().toArray(new String[0]);
    }
} 