---  
title: Manual para Autores
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-12  
modified: 2025-06-12  
author: "Juanjo Ruiz"  
---  
 
# Manual para Autores de cursos en formato YAML

## 1. Introducción

Este manual explica cómo crear cursos para la aplicación de aprendizaje en formato YAML.  

Los cursos pueden ser de cualquier temática (idiomas, programación, cultura general, etc.) y se editan con cualquier editor de texto.  

El formato YAML es legible y flexible, pero es importante respetar la estructura y los campos requeridos para cada tipo de pregunta.

---

## 2. Estructura general de un curso

Un curso se compone de:
- Un bloque de cabecera con metadatos.
- Una lista de bloques de contenido.
- Cada bloque contiene una lista de preguntas o pantallas de aprendizaje.

**Ejemplo básico:**

```yaml
id: curso_ejemplo_01
nombre: "Curso de ejemplo"
descripcion: "Un curso de muestra para autores"
bloques:
  - id: bloque1
    titulo: "Vocabulario básico"
    tipo: "test"
    preguntas:
      - id: p1
        tipo: "test"
        enunciado: "¿Cuál es la capital de Francia?"
        opciones:
          - "Madrid"
          - "París"
          - "Roma"
        respuestaCorrecta: "París"
      - id: p2
        tipo: "test"
        enunciado: "¿Cuál es el color del cielo?"
        opciones:
          - "Azul"
          - "Verde"
          - "Rojo"
        respuestaCorrecta: "Azul"
  - id: bloque2
    titulo: "Completar frases"
    tipo: "completar_huecos"
    preguntas:
      - id: p3
        tipo: "completar_huecos"
        enunciado: "La capital de España es _____."
        respuestaCorrecta: "Madrid"
  - id: bloque3
    titulo: "Flashcards"
    tipo: "flashcards"
    preguntas:
      - id: p4
        tipo: "flashcard"
        anverso: "Hola"
        reverso: "Hello"
```

---

## 3. Tipos de pregunta y campos obligatorios

### 3.1 Pregunta tipo test

- **Campos obligatorios:**
  - `id`: Identificador único.
  - `tipo`: "test"
  - `enunciado`: Texto de la pregunta.
  - `opciones`: Lista de opciones de respuesta.
  - `respuestaCorrecta`: Texto exacto de la opción correcta.

**Ejemplo:**
```yaml
- id: p1
  tipo: "test"
  enunciado: "¿Cuál es la capital de Francia?"
  opciones:
    - "Madrid"
    - "París"
    - "Roma"
  respuestaCorrecta: "París"
```

---

### 3.2 Pregunta de completar huecos

- **Campos obligatorios:**
  - `id`
  - `tipo`: "completar_huecos"
  - `enunciado`: Frase con hueco (usa ____ para indicar el hueco).
  - `respuestaCorrecta`: Palabra o frase que completa el hueco.

**Ejemplo:**
```yaml
- id: p3
  tipo: "completar_huecos"
  enunciado: "La capital de España es _____."
  respuestaCorrecta: "Madrid"
```

---

### 3.3 Flashcard

- **Campos obligatorios:**
  - `id`
  - `tipo`: "flashcard"
  - `anverso`: Texto o imagen del lado frontal.
  - `reverso`: Texto o imagen del lado trasero.

**Ejemplo:**
```yaml
- id: p4
  tipo: "flashcard"
  anverso: "Hola"
  reverso: "Hello"
```

---

## 4. Reglas y recomendaciones

- Cada bloque debe tener un campo `tipo` que indique el tipo de preguntas que contiene.
- Los identificadores (`id`) deben ser únicos dentro del curso.
- Puedes mezclar diferentes tipos de bloques y preguntas en un mismo curso.
- Si tu curso requiere imágenes o audios, colócalos en la misma carpeta y referencia el archivo en el campo correspondiente (ejemplo: `anverso: "foto.png"`).
- Revisa siempre que todos los campos obligatorios estén presentes para evitar errores de carga.



## Validación

Antes de compartir tu curso, utiliza el validador de cursos (si está disponible) en la aplicación) o revisa que la estructura YAML sea correcta y que todos los campos requeridos estén presentes.


## Referencia Rápida
**Tabla resumen** de campos *obligatorios* y *opcionales* para cada tipo de pregunta.

| Tipo de pregunta       | Campos obligatorios                                                                | Campos opcionales            
|------------------------|------------------------------------------------------------------------------------|---------------------------------
| **test**               | `id`, `tipo`, `enunciado`, `opciones`, `respuestaCorrecta`                         | `explicacion`, `imagen`          
| **completar_huecos**   | `id`, `tipo`, `enunciado`, `respuestaCorrecta`                                     | `explicacion`, `imagen`          
| **flashcard**          | `id`, `tipo`, `anverso`, `reverso`                                                 | `imagenAnverso`, `imagenReverso` 
| **traduccion**         | `id`, `tipo`, `enunciado`, `respuestaCorrecta`                                     | `explicacion`, `imagen`          
| **otro (extensible)**  | `id`, `tipo`, campos definidos por el nuevo tipo (consultar documentación técnica) | Depende del tipo                 

---

### Ejemplos de cada tipo

**Pregunta tipo test**
```yaml
- id: p1
  tipo: "test"
  enunciado: "¿Cuál es la capital de Francia?"
  opciones:
    - "Madrid"
    - "París"
    - "Roma"
  respuestaCorrecta: "París"
```

**Pregunta de completar huecos**
```yaml
- id: p2
  tipo: "completar_huecos"
  enunciado: "La capital de España es _____."
  respuestaCorrecta: "Madrid"
```

**Flashcard**
```yaml
- id: p3
  tipo: "flashcard"
  anverso: "Hola"
  reverso: "Hello"
```

**Pregunta de traducción**
```yaml
- id: p4
  tipo: "traduccion"
  enunciado: "Traduce: 'Good morning'"
  respuestaCorrecta: "Buenos días"
```

