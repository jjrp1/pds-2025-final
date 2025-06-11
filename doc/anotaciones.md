---  
title: Apuntes de anotaciones 
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-11  
modified: 2025-06-11  
author: "Juanjo Ruiz"  
---  
 
# Apuntes de anotaciones  

> **MEJORA**: marcar preguntas para repasar  
 
- Modelado conceptual
- Casos de uso
- Detalle de los casos de uso más importantes


# Consideraciones previas



Se trata de un enunciado para un proyecto práctico en grupo, inspirado en aplicaciones de aprendizaje como Duolingo y sistemas de tarjetas (flashcards) como Mochi. El objetivo es desarrollar una aplicación que permita aprender y repasar conceptos de cualquier dominio (idiomas, programación, música, etc.), mostrando tarjetas o ejercicios uno a uno, con diferentes tipos de preguntas (test, completar huecos, traducir, flashcards, etc.), y que sea fácilmente extensible para añadir nuevos tipos de ejercicios[1].

### Características mínimas:

- Al menos 3 tipos de preguntas/pantallas de aprendizaje distintas
- Facilidad para añadir nuevas.
- Elección de estrategia de aprendizaje por parte del usuario (secuencial, repetición espaciada, aleatoria, etc.).
- **Registro de sesiones**  
  Posibilidad de guardar y reanudar el estado actual del curso.
  
- **Registro de estadísticas**
  Registros de uso (tiempo, rachas, etc.).

- Formato YAML.

- Cada grupo debe proponer e integrar una característica adicional propia[1].

Sobre la realización:

- El trabajo es en grupos de 3.
- Todo debe estar en un repositorio de GitHub, usando GitHub para la gestión del proyecto.
- Se requiere modelado conceptual, definición y desarrollo de casos de uso, implementación en Java, uso de Maven, persistencia con JPA y pruebas de software[1].

Hay tres entregas principales (parcial 1, parcial 2 y final), cada una con requisitos específicos sobre casos de uso, modelo de dominio, implementación, tests y la característica adicional[1].

¿Te gustaría que te ayude a definir el dominio, los casos de uso, o tienes dudas sobre algún punto específico del enunciado?

[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/27407806/5840d9f6-67d2-40d4-9801-b110f5513311/enunciado.pdf




























### Implementación de *flashcards*



# Dinámica

Al arrancar la aplicación:
- Buscar cursos disponibles (carpeta "cursos" con cursos en formato YAML)
- De entre los cursos disponibles, buscar si alguno está empezado
- Buscar si hay cursos empezados (registro de actividad en sqlite)


# Diseño de la Interfaz GUI


- Al mostrar la pregunta arrancamos" el correspondiente temporizador.
- La interfaz solo permitirá avanzar.
- Tras cada preguntar, al evaluar su corrección, en caso de error, se mostrará la respuesta correcta, esperando una confirmación del usuario para pasar a la siguiente pregunta, este tiempo NO se registrará como tiempo de respuesta.
- Implementar retroceder implicaría registrar los sucesivos reintentos, además de permitir

  