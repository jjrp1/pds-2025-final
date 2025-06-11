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
