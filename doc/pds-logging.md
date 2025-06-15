# Sistema de Logging

## Descripción General

El sistema de logging de la aplicación está implementado utilizando SLF4J como fachada de logging y Logback como implementación. Esta combinación proporciona un sistema de logging robusto, flexible y de alto rendimiento.

## Arquitectura

### Componentes Principales

1. **PDSLoggerFactory (Singleton)**
   - Ubicación: `com.pds.util.PDSLoggerFactory`
   - Responsabilidad: Proporcionar instancias de logger configuradas
   - Implementa el patrón Singleton para garantizar una única instancia

2. **Configuración de Logback**
   - Ubicación: `src/main/resources/logback.xml`
   - Configura los appenders y niveles de logging

### Dependencias

Para implementar el sistema de logging, se han añadido las siguientes dependencias al archivo `pom.xml` del proyecto:

```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.11</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.4.14</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.4.14</version>
    </dependency>
</dependencies>
```

#### Explicación de las Dependencias

1. **SLF4J API (slf4j-api)**
   - Es la fachada de logging que proporciona una interfaz común para diferentes implementaciones de logging
   - Permite cambiar la implementación de logging sin modificar el código de la aplicación
   - Versión 2.0.11: La más reciente y estable, con mejoras en rendimiento y seguridad

2. **Logback Classic (logback-classic)**
   - Implementación nativa de SLF4J
   - Proporciona todas las funcionalidades de logging necesarias
   - Versión 1.4.14: Compatible con Java 8 y superiores
   - Incluye soporte para MDC (Mapped Diagnostic Context)

3. **Logback Core (logback-core)**
   - Componente base de Logback
   - Proporciona las clases fundamentales para el funcionamiento del sistema
   - Necesario para que Logback Classic funcione correctamente

#### Razones de la Elección

1. **Separación de Interfaz e Implementación**
   - SLF4J como fachada permite cambiar la implementación si es necesario
   - Facilita las pruebas unitarias al poder mockear el logging

2. **Rendimiento**
   - Logback es más rápido que otras implementaciones como Log4j
   - Mejor manejo de la memoria y recursos

3. **Flexibilidad**
   - Configuración mediante XML o programáticamente
   - Soporte para múltiples appenders
   - Rotación automática de logs

4. **Mantenimiento Activo**
   - Proyectos activamente mantenidos
   - Correcciones de seguridad regulares
   - Buena documentación y soporte de la comunidad

## Configuración

### Appenders

1. **CONSOLE**
   - Escribe logs en la consola
   - Nivel: INFO
   - Formato: `%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n`

2. **FILE**
   - Escribe logs en archivo
   - Nivel: DEBUG
   - Formato: `%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n`
   - Rotación diaria
   - Retención: 30 días

### Niveles de Logging

- **DEBUG**: Información detallada para diagnóstico
- **INFO**: Confirmación de que las cosas están funcionando
- **WARN**: Situaciones que requieren atención pero no son errores
- **ERROR**: Errores que impiden que una funcionalidad específica funcione

## Uso

### Obtener un Logger

```java
private final Logger logger = PDSLoggerFactory.getInstance().getLogger(MyClass.class);
```

### Ejemplos de Uso

```java
// Logging de información general
logger.info("Inicializando componente: {}", componentName);

// Logging de errores
try {
    // código que puede fallar
} catch (Exception e) {
    logger.error("Error al procesar la operación: {}", operationName, e);
}

// Logging de advertencias
logger.warn("Recurso no encontrado: {}", resourceName);

// Logging de debug
logger.debug("Procesando elemento: {} con configuración: {}", elementId, config);
```

## Mejores Prácticas

1. **Niveles de Logging**
   - Usar DEBUG para información detallada de diagnóstico
   - Usar INFO para eventos importantes del sistema
   - Usar WARN para situaciones que requieren atención
   - Usar ERROR solo para errores reales

2. **Mensajes**
   - Ser específico y descriptivo
   - Incluir contexto relevante
   - Usar marcadores de posición `{}` en lugar de concatenación
   - Incluir excepciones como último parámetro en caso de error

3. **Rendimiento**
   - Evitar operaciones costosas en mensajes de DEBUG
   - Usar comprobación de nivel antes de construir mensajes complejos
   - Mantener los mensajes concisos pero informativos

## Mantenimiento

### Rotación de Logs

- Los archivos de log se rotan diariamente
- Se mantiene un historial de 30 días
- Los archivos antiguos se eliminan automáticamente

### Monitoreo

- Revisar regularmente los logs para detectar patrones de error
- Configurar alertas para errores críticos
- Mantener un registro de problemas recurrentes

## Troubleshooting

### Problemas Comunes

1. **Logs no aparecen**
   - Verificar la configuración de niveles en logback.xml
   - Comprobar permisos de escritura en el directorio de logs
   - Verificar que las dependencias están correctamente configuradas

2. **Rendimiento**
   - Reducir el nivel de logging en producción
   - Optimizar patrones de formato
   - Considerar usar logging asíncrono para operaciones intensivas

## Referencias

- [Documentación de SLF4J](https://www.slf4j.org/manual.html)
- [Documentación de Logback](https://logback.qos.ch/documentation.html)
- [Mejores Prácticas de Logging](https://www.slf4j.org/faq.html) 