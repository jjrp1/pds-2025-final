---  
title: Arquitectura Microkernel  
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-12  
modified: 2025-06-12  
author: "Juanjo Ruiz"  
---  
 
# Arquitectura Microkernel  

La aplicación implementa una arquitectura microkernel para gestionar los diferentes tipos de preguntas que pueden existir en el sistema. Esta arquitectura permite:

- Cargar dinámicamente nuevos tipos de preguntas sin modificar el código base
- Extender la funcionalidad del sistema de forma modular
- Mantener el núcleo del sistema estable y aislado de los cambios en los tipos de preguntas
- Facilitar la adición de nuevos tipos de preguntas por parte de desarrolladores externos

## Componentes Principales

### 1. Núcleo del Sistema

#### Pregunta (Interfaz)
```java
public interface Pregunta {
    Long getId();
    String getTipo();
    String getEnunciado();
    boolean verificarRespuesta(String respuesta);
    String getRespuestaCorrecta();
}
```
- Define la interfaz base que deben implementar todos los tipos de preguntas
- Establece los métodos comunes que toda pregunta debe proporcionar
- Permite la interoperabilidad entre diferentes tipos de preguntas

#### PluginPregunta (Interfaz)
```java
public interface PluginPregunta {
    String getTipo();
    Pregunta crearPregunta(Long id, String enunciado, Object datos);
    boolean validarDatos(Object datos);
}
```
- Define la interfaz que deben implementar todos los plugins
- Establece el contrato para crear y validar preguntas de un tipo específico
- Permite la carga dinámica de nuevos tipos de preguntas

#### GestorPluginsPreguntas (Microkernel)
```java
public class GestorPluginsPreguntas {
    private static GestorPluginsPreguntas instancia;
    private final Map<String, PluginPregunta> plugins;
    private final File directorioPlugins;
    
    // Métodos principales
    public void cargarPlugins();
    public PluginPregunta obtenerPlugin(String tipo);
    public Pregunta crearPregunta(String tipo, Long id, String enunciado, Object datos);
    public boolean esTipoSoportado(String tipo);
    public String[] obtenerTiposSoportados();
}
```
- Implementa el microkernel que gestiona los plugins
- Mantiene un registro de los plugins cargados
- Proporciona métodos para crear preguntas de diferentes tipos
- Gestiona la carga dinámica de plugins desde el directorio de plugins

### 2. Plugin de Ejemplo: PreguntaTest

#### PreguntaTest
```java
public class PreguntaTest implements Pregunta {
    private final Long id;
    private final String enunciado;
    private final List<String> opciones;
    private final int opcionCorrecta;
    
    // Implementación de los métodos de Pregunta
}
```
- Implementa la interfaz Pregunta para preguntas de tipo test
- Almacena las opciones y la respuesta correcta
- Proporciona la lógica específica para validar respuestas

#### PluginPreguntaTest
```java
public class PluginPreguntaTest implements PluginPregunta {
    @Override
    public String getTipo() { return "test"; }
    
    @Override
    public Pregunta crearPregunta(Long id, String enunciado, Object datos) {
        // Lógica para crear una pregunta de tipo test
    }
    
    @Override
    public boolean validarDatos(Object datos) {
        // Validación de datos específica para preguntas de tipo test
    }
}
```
- Implementa la interfaz PluginPregunta
- Proporciona la lógica para crear y validar preguntas de tipo test
- Define el formato de datos específico para este tipo de pregunta

## Proceso de Carga de Plugins

1. Al iniciar la aplicación, el GestorPluginsPreguntas busca archivos JAR en el directorio `plugins/`
2. Para cada JAR encontrado:
   - Crea un ClassLoader específico para el JAR
   - Busca implementaciones de PluginPregunta usando ServiceLoader
   - Registra cada plugin encontrado en el mapa de plugins

## Creación de Nuevos Plugins

Para crear un nuevo tipo de pregunta, los desarrolladores deben:

1. Crear una nueva clase que implemente la interfaz `Pregunta`
2. Crear un plugin que implemente la interfaz `PluginPregunta`
3. Crear un archivo de servicio en `META-INF/services/com.pds.core.PluginPregunta`
4. Empaquetar el plugin como JAR
5. Colocar el JAR en el directorio `plugins/` de la aplicación

### Estructura del JAR del Plugin
```
plugin.jar
├── META-INF/
│   └── services/
│       └── com.pds.core.PluginPregunta
└── com/
    └── pds/
        └── plugins/
            ├── MiTipoPregunta.java
            └── PluginMiTipoPregunta.java
```

## Ventajas de la Arquitectura

1. **Extensibilidad**: Fácil adición de nuevos tipos de preguntas
2. **Modularidad**: Cada tipo de pregunta está aislado en su propio plugin
3. **Mantenibilidad**: El núcleo del sistema permanece estable
4. **Flexibilidad**: Los plugins pueden ser cargados/descargados dinámicamente
5. **Seguridad**: Validación de datos específica para cada tipo de pregunta

## Consideraciones de Implementación

1. **Validación de Datos**: Cada plugin debe implementar su propia lógica de validación
2. **Manejo de Errores**: El microkernel debe manejar adecuadamente los errores de carga de plugins
3. **Versionado**: Los plugins deben ser compatibles con la versión del núcleo
4. **Aislamiento**: Cada plugin debe funcionar de forma independiente
5. **Persistencia**: Los datos de las preguntas deben ser serializables

 
