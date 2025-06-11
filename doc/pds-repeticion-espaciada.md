---  
title: Apuntes de pds-repeticion-espaciada 
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-11  
modified: 2025-06-11  
author: "Juanjo Ruiz"  
---  
 
# Estrategia de Aprendizaje - Repetición Espaciada 

La repetición espaciada es una técnica de aprendizaje que optimiza el tiempo de estudio mostrando las preguntas justo cuando el estudiante está a punto de olvidarlas, basándose en el "efecto espaciado" de la psicología cognitiva.

## Componentes Clave del Sistema

1. **Modelo de Memoria**: Cada pregunta necesita un perfil que registre:
   - Frecuencia de repeticiones
   - Historial de respuestas (correctas/incorrectas)
   - Intervalo actual entre repeticiones
   - Factor de facilidad (qué tan bien se recuerda)

2. **Algoritmo de Programación**:
   - Determina cuándo mostrar cada pregunta nuevamente
   - Ajusta los intervalos basándose en la dificultad percibida

## Algoritmo de Repetición Espaciada (SM-2 simplificado)

1. **Primera vez**: La pregunta se muestra como nueva
2. **Respuesta correcta**:
   - Aumenta el intervalo para la próxima repetición
   - Incrementa el factor de facilidad (la pregunta se considera más fácil)
3. **Respuesta incorrecta**:
   - Reinicia el intervalo a corto plazo
   - Disminuye el factor de facilidad (la pregunta se considera más difícil)

## Secuencia de Generación de Preguntas

1. **Priorización**:
   - Las preguntas con intervalos vencidos tienen máxima prioridad
   - Las preguntas con más errores recientes tienen mayor prioridad
   - Las preguntas nuevas se intercalan estratégicamente

2. **Mecanismo de Selección**:
   - Cada día/sesión tiene un "presupuesto" de repeticiones
   - Se seleccionan primero las preguntas más urgentes (según el modelo de memoria)
   - Se añaden preguntas nuevas de forma gradual

3. **Adaptación**:
   - El sistema ajusta automáticamente la dificultad percibida
   - Los intervalos crecen exponencialmente para preguntas bien recordadas
   - Los intervalos se reducen para preguntas problemáticas

## Integración con otras estrategias

El sistema debe permitir:
- Cambiar entre estrategias (secuencial, aleatoria, espaciada)
- Reiniciar el historial de repetición espaciada si se desea
- Mostrar estadísticas de progreso basadas en el algoritmo

Esta implementación optimiza el tiempo de estudio mostrando las preguntas en el momento más efectivo para la retención a largo plazo.


# Implementación

Sí, la implementación de iteradores para diferentes estrategias de visualización de preguntas (secuencial, aleatoria, repetición espaciada) sigue claramente el **patrón de diseño Iterator**, pero con algunas variantes y complementos de otros patrones. Aquí el análisis detallado:

---

### 1. **Patrón Iterator (Principal)**
**Propósito**:  
Proporcionar una forma de acceder secuencialmente a los elementos de un objeto agregado (como la lista de preguntas) sin exponer su representación interna.

**Cómo se aplica**:
- Cada estrategia (`Secuencial`, `Aleatoria`, `Repetición Espaciada`) es una implementación concreta de un iterador.
- Todos comparten la misma interfaz (`QuestionIterator`), con métodos como `__next__()` y `record_response()`.
- El cliente (ej: el sistema de preguntas) puede cambiar entre iteradores sin modificar su lógica principal.

**Ventaja**:  
- **Desacoplamiento**: El código que muestra las preguntas no necesita saber cómo se seleccionan.

---

### 2. **Patrón Strategy (Complementario)**
**Propósito**:  
Definir una familia de algoritmos (estrategias de recorrido), encapsular cada una y hacerlas intercambiables.

**Cómo se aplica**:
- Cada iterador (`Secuencial`, `Aleatorio`, etc.) es una "estrategia" para seleccionar preguntas.
- El usuario puede cambiar dinámicamente entre estrategias (como seleccionar "modo aleatorio" desde la UI).

**Relación con Iterator**:  
- Mientras Iterator se enfoca en el **acceso secuencial**, Strategy se enfoca en la **intercambiabilidad de algoritmos**. Ambos patrones son complementarios aquí.

---

### 3. **Patrón Factory Method (Opcional)**
**Cómo podría aplicarse**:  
Si la creación de iteradores es compleja (ej: necesitan configuraciones distintas), podría usarse un factory para instanciarlos:

```python
def create_iterator(strategy_type, questions):
    if strategy_type == "sequential":
        return SequentialIterator(questions)
    elif strategy_type == "random":
        return RandomIterator(questions)
    elif strategy_type == "spaced":
        return SpacedRepetitionIterator(questions)
```

---

### 4. **Estado (State) para la repetición espaciada**
**Propósito**:  
Gestionar el comportamiento cambiante de cada pregunta según su estado de memorización (nueva, en aprendizaje, en repaso).

**Cómo se aplica**:  
- Cada pregunta en `SpacedRepetitionIterator` podría tener un objeto `Estado` que determina cuándo debe mostrarse nuevamente.
- Los estados típicos serían:  
  - `Nueva` → Mostrar pronto.  
  - `En aprendizaje` → Intervalos cortos.  
  - `En repaso` → Intervalos largos.  

---

### Diagrama conceptual simplificado:
```
[Cliente] → [QuestionIterator] (Interfaz)
               ↑    ↑    ↑
      [Secuencial] [Aleatorio] [Espaciada] (Implementaciones concretas)
```

---

### ¿Por qué estos patrones son adecuados?
1. **Iterator**:  
   - Ideal para recorrer colecciones de formas variadas.  
   - Oculta detalles como el orden de las preguntas o la lógica de espaciado.  

2. **Strategy**:  
   - Permite añadir nuevas estrategias (ej: "mostrar solo preguntas difíciles") sin modificar el código existente.  

3. **Extensibilidad**:  
   - Si luego quieres añadir un iterador que priorice preguntas mal contestadas, solo necesitas una nueva clase que implemente `QuestionIterator`.

---

### Conclusión
La implementación sigue principalmente el **patrón Iterator**, pero combina ideas de **Strategy** para la intercambiabilidad de algoritmos y potencialmente **State** para gestionar la complejidad de la repetición espaciada. Esta mezcla es común en sistemas donde el acceso a datos y los algoritmos de selección son clave.