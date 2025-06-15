package com.pds.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fábrica de loggers que implementa el patrón Singleton.
 * Proporciona instancias de logger configuradas para diferentes clases.
 */
public class PDSLoggerFactory {
    private static PDSLoggerFactory instance;
    
    private PDSLoggerFactory() {
        // Constructor privado para el patrón Singleton
    }
    
    /**
     * Obtiene la instancia única de PDSLoggerFactory.
     * @return La instancia de PDSLoggerFactory
     */
    public static synchronized PDSLoggerFactory getInstance() {
        if (instance == null) {
            instance = new PDSLoggerFactory();
        }
        return instance;
    }
    
    /**
     * Obtiene un logger para la clase especificada.
     * @param clazz La clase para la que se quiere obtener el logger
     * @return Una instancia de Logger configurada
     */
    public Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Obtiene un logger con el nombre especificado.
     * @param name El nombre del logger
     * @return Una instancia de Logger configurada
     */
    public Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
} 