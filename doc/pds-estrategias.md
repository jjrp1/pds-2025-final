---  
title: Detalle de Estrategias 
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-11  
modified: 2025-06-11  
author: "Juanjo Ruiz"  
---  

# Estrategias de Aprendizaje

Este documento describe las diferentes estrategias de aprendizaje implementadas en el sistema, su funcionamiento y cómo se integran con la interfaz de usuario.

## Estrategias Disponibles

### 1. Secuencial
- Presenta las preguntas en orden secuencial
- Mantiene un índice de la pregunta actual
- Ideal para aprendizaje estructurado y progresivo

### 2. Aleatoria
- Presenta las preguntas en orden aleatorio
- Mantiene un registro de preguntas ya mostradas
- Útil para repaso y evitar sesgos de orden

### 3. Repetición Espaciada
- Utiliza el algoritmo SM-2 para optimizar el tiempo de estudio
- Ajusta los intervalos según el rendimiento del usuario
- Ideal para retención a largo plazo

## Integración con la GUI

### Selección de Estrategia
- Al comenzar un nuevo curso, se muestra un diálogo para seleccionar la estrategia
- Si se reanuda una sesión, se usa la estrategia guardada
- La interfaz se limpia completamente al comenzar/reanudar

### Ámbito de Aplicación
- La estrategia elegida se aplica a nivel de bloque, no de curso completo
- Cada bloque mantiene su propia secuencia de preguntas según la estrategia
- Al cambiar de bloque, se reinicia la secuencia manteniendo la misma estrategia
- Por ejemplo, con estrategia aleatoria:
  - Las preguntas se "barajan" dentro de cada bloque
  - No se mezclan preguntas de diferentes bloques
  - Al pasar al siguiente bloque, se barajan sus preguntas independientemente

## Implementación Técnica

### Interfaz Base
```java
public interface EstrategiaAprendizaje extends Serializable {
    Pregunta siguientePregunta();
    EstadoIterador guardarEstado();
    void restaurarEstado(EstadoIterador estado);
    String getNombre();
    String getDescripcion();
}
```

### Estado del Iterador
```java
public class EstadoIterador implements Serializable {
    private int indiceActual;
    private List<Integer> ordenPreguntas;
    private Map<String, Object> datosEstrategia;
    private String idBloque;  // Identificador del bloque actual
    
    // Getters y setters
}
```

### Ejemplo de Implementación
```java
public class EstrategiaSecuencial implements EstrategiaAprendizaje {
    private int indiceActual = 0;
    private List<Pregunta> preguntas;
    private String idBloque;
    
    public EstrategiaSecuencial(List<Pregunta> preguntas, String idBloque) {
        this.preguntas = preguntas;
        this.idBloque = idBloque;
    }
    
    @Override
    public Pregunta siguientePregunta() {
        if (indiceActual < preguntas.size()) {
            return preguntas.get(indiceActual++);
        }
        return null;
    }
    
    @Override
    public EstadoIterador guardarEstado() {
        EstadoIterador estado = new EstadoIterador();
        estado.setIndiceActual(indiceActual);
        estado.setIdBloque(idBloque);
        return estado;
    }
    
    @Override
    public void restaurarEstado(EstadoIterador estado) {
        this.indiceActual = estado.getIndiceActual();
        this.idBloque = estado.getIdBloque();
    }
    
    @Override
    public String getNombre() {
        return "Secuencial";
    }
    
    @Override
    public String getDescripcion() {
        return "Presenta las preguntas en orden secuencial dentro de cada bloque";
    }
}
```

## Persistencia

### Guardado de Sesión
- Se serializa el estado completo de la estrategia
- Se guarda junto con el progreso de la sesión
- Permite reanudar exactamente donde se dejó
- Se mantiene el estado de la estrategia por bloque

### Restauración
- Se deserializa el estado de la estrategia
- Se reconstruye el iterador con su estado anterior
- Se restaura el punto exacto de avance
- Se recupera el estado específico del bloque actual

## Consideraciones de Diseño

1. **Extensibilidad**
   - Fácil adición de nuevas estrategias
   - Interfaz clara y bien definida
   - Serialización flexible

2. **Integración con GUI**
   - Transiciones suaves entre bloques
   - Feedback claro al usuario
   - Persistencia transparente

3. **Rendimiento**
   - Carga eficiente de estados
   - Minimización de operaciones I/O
   - Caché de estados cuando sea posible

---

# Registro de sesión (iterador de estrategia) 
 
Queremos **guardar el estado de la sesión** en todo momento, al objeto de ***reanudar la sesión*** en un futuro. 

Para cada sesión, se escoge una estrategia de aprendizaje (secuencial, aleatoria, repetición espaciada, etc.) que determina el orden de presentación de las preguntas, y por tanto el "iterador" sobre las preguntas debe ser persistente.

## Enfoque recomendado

### 1. **Modelo conceptual**
- Cada **sesión** debe guardar:
  - El identificador del curso y la estrategia elegida.
  - El estado del "iterador": cuál fue la última pregunta presentada, y cualquier estado interno necesario para la estrategia (por ejemplo, el índice actual en secuencial, el orden de preguntas en aleatoria, el historial en repetición espaciada, etc.).

### 2. **Implementación del iterador persistente**
- **Serializa el estado del iterador** junto con el progreso de la sesión.
- Por ejemplo, para una estrategia secuencial basta con guardar el índice de la pregunta actual.  
  Para una estrategia aleatoria, puedes guardar la lista de índices ya visitados y el siguiente a mostrar.
- En Java, puedes modelar esto con una clase abstracta `EstrategiaIterador`, con subclases para cada estrategia, y asegurarte de que todas sean serializables y persistentes (por ejemplo, usando JPA o serialización manual en JSON/YAML).

### 3. **Persistencia**
- Guarda en la base de datos (SQLite/JPA) el estado de la sesión, incluyendo:
  - ID del curso
  - Estrategia seleccionada
  - Estado serializado del iterador (puede ser un campo JSON, BLOB o similar)
  - Pregunta actual (o el identificador necesario para reconstruir el iterador)
  - Estadísticas de la sesión

### 4. **Reanudación**
- Al reanudar, recupera el estado de la sesión y reconstruye el iterador con su estado anterior, permitiendo continuar exactamente donde se dejó.

---

## Ejemplo simplificado en Java

```java
public abstract class EstrategiaIterador implements Serializable {
    public abstract Pregunta siguientePregunta();
    public abstract void guardarEstado();
    public abstract void cargarEstado();
}

public class IteradorSecuencial extends EstrategiaIterador {
    private int indiceActual;
    // métodos para avanzar, guardar y cargar estado
}

public class Sesion {
    private Curso curso;
    private EstrategiaIterador iterador;
    private Date fechaInicio;
    // otros campos de progreso y estadísticas
}
```

Al guardar la sesión, serializas el estado del iterador (por ejemplo, con JSON o como campo BLOB). Al reanudar, lo deserializas y continúas.



¡Perfecto! Aquí tienes un **ejemplo detallado** de cómo serializar y deserializar el estado del iterador de una estrategia en Java, pensado para que puedas implementarlo fácilmente y documentarlo en tu proyecto.

---

## Ejemplo: Serialización y deserialización del iterador de estrategia

### 1. **Definición de la interfaz EstrategiaAprendizaje**

```java
import java.io.Serializable;

public interface EstrategiaAprendizaje extends Serializable {
    Pregunta siguientePregunta();
    EstadoIterador guardarEstado();
    void restaurarEstado(EstadoIterador estado);
}
```

### 2. **Clase de estado serializable para el iterador**

```java
import java.io.Serializable;

public class EstadoIterador implements Serializable {
    private int indiceActual;
    private List ordenPreguntas; // Por si la estrategia es aleatoria
    // Otros campos según la estrategia

    // Getters y setters
    public int getIndiceActual() { return indiceActual; }
    public void setIndiceActual(int indiceActual) { this.indiceActual = indiceActual; }

    public List getOrdenPreguntas() { return ordenPreguntas; }
    public void setOrdenPreguntas(List ordenPreguntas) { this.ordenPreguntas = ordenPreguntas; }
}
```

### 3. **Ejemplo de implementación de una estrategia**

```java
public class EstrategiaSecuencial implements EstrategiaAprendizaje {
    private int indiceActual = 0;
    private List preguntas;

    public EstrategiaSecuencial(List preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public Pregunta siguientePregunta() {
        if (indiceActual < preguntas.size()) {
            return preguntas.get(indiceActual++);
        }
        return null;
    }

    @Override
    public EstadoIterador guardarEstado() {
        EstadoIterador estado = new EstadoIterador();
        estado.setIndiceActual(indiceActual);
        return estado;
    }

    @Override
    public void restaurarEstado(EstadoIterador estado) {
        this.indiceActual = estado.getIndiceActual();
    }
}
```

### 4. **Uso en la clase Sesión**

```java
public class Sesion {
    private EstrategiaAprendizaje estrategia;
    private EstadoIterador estadoIterador;

    public void guardarSesion() {
        this.estadoIterador = estrategia.guardarEstado();
        // Aquí persistirías el estadoIterador (como campo serializado, JSON, etc.)
    }

    public void restaurarSesion() {
        if (estadoIterador != null) {
            estrategia.restaurarEstado(estadoIterador);
        }
    }
}
```

### 5. **Persistencia con JPA**

Puedes guardar el estado del iterador como un campo serializado (por ejemplo, un BLOB o un campo JSON) en la entidad Sesión:

```java
import javax.persistence.*;

@Entity
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] estadoIteradorSerializado;

    @Transient
    private EstadoIterador estadoIterador;

    // Métodos para serializar y deserializar el estado
    public void serializarEstadoIterador() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(estadoIterador);
        out.flush();
        estadoIteradorSerializado = bos.toByteArray();
    }

    public void deserializarEstadoIterador() throws IOException, ClassNotFoundException {
        if (estadoIteradorSerializado != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(estadoIteradorSerializado);
            ObjectInputStream in = new ObjectInputStream(bis);
            estadoIterador = (EstadoIterador) in.readObject();
        }
    }
}
```
