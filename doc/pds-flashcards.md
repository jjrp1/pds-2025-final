---  
title: Sobre las *flashcards*
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-11  
modified: 2025-06-11  
author: "Juanjo Ruiz"  
---  
 
# Sobre las *flashcards*

Las *flashcards* presentan un reto particular porque, por definición, no requieren respuesta explícita del usuario, sino que están pensadas para el autoestudio y la autoevaluación. 

Posibles enfoques:

- **Opción 1: No se evalúan**  
  Simplemente se muestran como material de estudio y no afectan a la puntuación ni al avance. Se pueden registrar métricas como tiempo de visualización o número de veces revisada, pero no se considera “acierto” o “fallo”.

- **Opción 2: Evaluación por tiempo**  
  Se muestra la flashcard durante un tiempo determinado. Si el usuario la cierra antes de que pase ese tiempo, se asume que ha repasado correctamente; si se excede, podría considerarse como fallo o necesidad de repaso. Sin embargo, esto depende mucho de la honestidad y el ritmo personal del usuario.

- **Opción 3: Autoevaluación explícita**  
  Tras mostrar la respuesta, se pide al usuario que indique si la recordaba o no (por ejemplo, botones “Lo recordaba” / “No lo recordaba”). Esta opción es muy utilizada en aplicaciones de repetición espaciada y flashcards (como Anki o Mochi), y aunque depende de la honestidad del usuario, fomenta la reflexión metacognitiva.

- **Opción 4: Seguimiento estadístico**  
  No se evalúa cada tarjeta individualmente, pero se registra cuántas veces ha sido revisada, el tiempo dedicado, y se usan estos datos para ajustar la repetición o la dificultad del curso.

- **Opción 5: Pruebas de repaso posteriores**  
  Las flashcards se usan como introducción al contenido, y más adelante se incluyen preguntas evaluables sobre ese contenido para comprobar si el usuario lo ha aprendido.

La opción más extendida y pedagógicamente útil suele ser la autoevaluación explícita (opción 3), ya que permite adaptar la dificultad y la frecuencia de repaso a la percepción real del usuario, aunque siempre existe la posibilidad de autoengaño. Complementar esto con seguimiento estadístico y pruebas de repaso puede equilibrar la experiencia y la evaluación del aprendizaje.





# SCORM (*Sharable Content Object Reference Model*) 

SCORM es un estándar internacional para la creación, empaquetado y distribución de contenidos de e-learning. Permite que los cursos sean reutilizables y compatibles entre diferentes plataformas LMS (Learning Management System) como Moodle, Blackboard, etc. SCORM define cómo estructurar los contenidos (módulos, lecciones, actividades) y cómo comunicar el progreso y los resultados del usuario entre el contenido y el LMS.

Implementar SCORM resulta evidentemente excesivo para este proyecto, sin embargo, veamos qué podemos aprender de SCORM


## Ideas y enfoques útiles de SCORM para tu aplicación

| Idea/Enfoque SCORM                | ¿Cómo puede ayudar a tu aplicación?                                                                                 |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| **Modularidad (SCOs)**             | Estructura tus cursos en módulos/bloques independientes y reutilizables, facilitando la creación y edición de cursos.|
| **Separación de contenido y lógica**| Mantén el contenido (preguntas, teoría, flashcards) separado de la lógica de navegación y evaluación.                |
| **Seguimiento de progreso**        | Inspírate en el modelo SCORM para registrar estado, puntuaciones, tiempo y progreso del usuario en cada bloque.      |
| **Metadatos de contenido**         | Añade metadatos (autor, fecha, nivel, objetivos) a cursos/bloques para facilitar la búsqueda y organización.         |
| **Reutilización de recursos**      | Permite que bloques o preguntas puedan ser reutilizados en diferentes cursos o itinerarios.                          |
| **Exportación/importación estructurada** | Estandariza la forma de empaquetar cursos (por ejemplo, en JSON/YAML bien estructurado) para facilitar compartirlos.|
| **Secuenciación flexible**         | Permite definir rutas de aprendizaje personalizadas, como hace SCORM con la secuenciación de SCOs.                  |


### ¿Qué podemos aprender/aprovechar de SCORM?

| Idea/Enfoque de SCORM                       | ¿Cómo puede ayudar a nuestra aplicación?                                                                                         |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| **Modularidad (SCOs, unidades reutilizables)** | Permitir que los cursos se dividan en bloques o módulos independientes y reutilizables, facilitando la creación y edición de contenidos. |
| **Separación de contenido y lógica**        | Mantener el contenido (preguntas, teoría, flashcards) separado de la lógica de navegación y evaluación, lo que mejora la flexibilidad y el mantenimiento. |
| **Seguimiento estandarizado del progreso**  | Implementar mecanismos para registrar el estado, puntuaciones, tiempo y avance del usuario en cada bloque o curso.              |
| **Metadatos de contenido**                  | Añadir información descriptiva (autor, nivel, objetivos, fecha) a cursos y bloques, facilitando la búsqueda y organización.     |
| **Reutilización de recursos**               | Permitir que bloques, preguntas o materiales puedan ser reutilizados en diferentes cursos o itinerarios de aprendizaje.         |
| **Exportación/importación estructurada**    | Estandarizar la forma de empaquetar cursos (por ejemplo, en JSON/YAML bien estructurado) para facilitar el intercambio y la colaboración. |
| **Secuenciación flexible**                  | Permitir definir rutas de aprendizaje personalizadas, como hace SCORM con la secuenciación de módulos, adaptando el flujo a las necesidades del usuario. |

---

## Ejemplos prácticos aplicables

1. **Estructura modular**  
   Puedes definir que un curso esté compuesto por bloques (como los SCOs de SCORM), y cada bloque puede contener preguntas de un tipo o mixtas, o incluso solo material teórico.

2. **Seguimiento del aprendizaje**  
   Guarda el progreso del usuario por bloque o por pregunta, incluyendo tiempo dedicado, intentos, aciertos, etc., similar al seguimiento SCORM (cmi.core.lesson_status, cmi.core.score, etc.).

3. **Metadatos**  
   Añade información descriptiva a cada curso o bloque, lo que facilita la búsqueda, el filtrado y la recomendación de contenidos.

4. **Reutilización y compartición**  
   Permite que los usuarios puedan importar/exportar bloques o cursos completos en un formato estándar, facilitando la colaboración y el intercambio.

5. **Secuenciación personalizada**  
   Permite definir el orden de presentación de los bloques/preguntas, o incluso rutas alternativas según el rendimiento del usuario (como la secuenciación SCORM).



# Tipos de preguntas

| Tipo de pregunta                | Complejidad de implementación | Dificultad de corrección | Comentarios a la corrección                                               |
|---------------------------------|-------------------------------|-------------------------|--------------------------------------------------------------------------|
| Flashcard simple                | Muy baja                      | Muy baja                | No se evalúa, solo se registra si fue revisada o no                      |
| Opción múltiple (test)          | Baja                          | Muy baja                | Comparación directa con la respuesta correcta                             |
| Verdadero/Falso                 | Baja                          | Muy baja                | Respuesta binaria, fácil de validar                                       |
| Completar huecos                | Baja                          | Baja                    | Puede requerir tolerancia a mayúsculas/minúsculas, sinónimos, errores menores |
| Emparejamiento                  | Media                         | Baja-media              | Validar pares correctos; puede haber múltiples combinaciones correctas    |
| Ordenar elementos               | Media                         | Baja-media              | Comparar secuencia; se puede permitir cierto margen de error              |
| Respuesta corta                 | Media                         | Media                   | Puede requerir manejo de sinónimos, variantes gramaticales, errores menores|
| Selección múltiple              | Media                         | Media                   | Validar todas las opciones seleccionadas; puede haber combinaciones parcialmente correctas |
| Respuesta larga (abierta)       | Media-alta                    | Muy alta                | Requiere revisión manual o IA avanzada para análisis semántico            |
| Arrastrar y soltar (drag & drop)| Alta                          | Media                   | Validar posiciones o asignaciones; puede ser visual                       |


### **Encuentra el intruso (Odd One Out)**

**Descripción:**  
Se muestran varias palabras, imágenes o frases. El usuario debe identificar cuál de ellas no pertenece al grupo por alguna característica (semántica, gramatical, lógica, etc.).

**Ventajas:**  
- Sencillo de implementar: solo requiere marcar la opción "intrusa".
- Corrección automática directa.
- Fomenta el razonamiento y la asociación de conceptos.

**Ejemplo YAML:**
```yaml
- id: "intruso1"
  tipo: "encuentra_intruso"
  enunciado: "¿Cuál de estas palabras no pertenece al grupo?"
  opciones:
    - "Dog"
    - "Cat"
    - "Car"
    - "Bird"
  respuesta_correcta: "Car"
```

---

### 2. **"Secuencia lógica" (Next in Sequence)**

**Descripción:**  
Se presenta una secuencia (de números, palabras, imágenes, símbolos, etc.) y el usuario debe elegir o escribir el elemento que sigue lógicamente.

**Ventajas:**  
- Sencillo de implementar: solo hay que definir la secuencia y la respuesta esperada.
- Corrección automática directa.
- Útil para trabajar patrones, lógica, series, progresiones y vocabulario.

**Ejemplo YAML:**
```yaml
- id: "secuencia1"
  tipo: "secuencia_logica"
  enunciado: "Completa la secuencia: Monday, Tuesday, Wednesday, ___"
  respuesta_correcta: "Thursday"
```

---

Ambos modelos son originales, fáciles de implementar y pueden adaptarse a cualquier dominio, desde idiomas hasta matemáticas o cultura general. Además, aportan variedad y fomentan el pensamiento crítico y la observación, alineándose con el enfoque lúdico y extensible de la aplicación descrita en el enunciado[1].

[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/27407806/5840d9f6-67d2-40d4-9801-b110f5513311/enunciado.pdf



### **Juego del ahorcado**
- **Ventajas pedagógicas:**  
  - Refuerza la ortografía, el vocabulario y la memoria.
  - Es motivador y lúdico, lo que mejora la retención y el compromiso.
  - Puede adaptarse a cualquier dominio donde haya que recordar palabras o conceptos (idiomas, ciencias, historia, etc.).
- **Dificultad de implementación:**  
  - Media: requiere lógica para gestionar letras, intentos y visualización del progreso, pero la corrección es sencilla (palabra acertada o no).
- **Corrección:**  
  - Muy clara y automática: la palabra se adivina correctamente o no.

### **Panel tipo “ruleta de la fortuna”**
- **Ventajas pedagógicas:**  
  - Similar al ahorcado, añade un componente de azar y gamificación.
  - Permite trabajar vocabulario, frases hechas, refranes, nombres propios, etc.
  - Puede usarse como refuerzo o repaso final en cualquier materia.
- **Dificultad de implementación:**  
  - Media-alta: requiere lógica para mostrar letras/panel, gestionar turnos (si es multijugador), y animaciones si se desea una experiencia más rica.
- **Corrección:**  
  - Sencilla: se valida si la frase/palabra es descubierta correctamente.

---

## **¿Por qué son buenas ideas?**
- Ambos tipos de preguntas/juegos aportan variedad y motivación, elementos clave en el aprendizaje moderno.
- Son fácilmente adaptables a diferentes dominios y niveles.
- Permiten la implementación de mecánicas de gamificación, que aumentan la implicación del usuario.
- Se pueden usar como actividad principal o como refuerzo/repaso.

---

## **¿Cómo integrarlos en tu sistema?**
- Puedes definir un tipo de pregunta “ahorcado” y otro “panel ruleta”, parametrizando la palabra o frase objetivo.
- Permitir que los creadores de cursos incluyan estos bloques en cualquier lección.
- Registrar estadísticas de aciertos, errores y tiempo, igual que en otros tipos de preguntas.

---

## **Conclusión**
Incluir preguntas tipo “ahorcado” o “ruleta de la fortuna” enriquecería mucho tu plataforma, aportando valor pedagógico y diferenciación.  
¡Son totalmente recomendables y muy bien recibidas por los usuarios!

¿Te gustaría un ejemplo de cómo modelar una pregunta de este tipo en YAML o cómo integrarla en el diseño de clases?

[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/27407806/5840d9f6-67d2-40d4-9801-b110f5513311/enunciado.pdf




# Modelo curso 

```yaml
curso:
  id: "idiomas-divertidos"
  titulo: "Aprende inglés jugando"
  descripcion: "Curso de inglés con actividades lúdicas para reforzar vocabulario y ortografía."
  bloques:
    - id: "ahorcado-vocabulario"
      tipo: "ahorcado"
      titulo: "Juego del ahorcado"
      descripcion: "Adivina la palabra en inglés relacionada con la definición dada."
      preguntas:
        - id: "a1"
          tipo: "ahorcado"
          definicion: "Animal que vuela de noche y usa ultrasonidos para orientarse."
          palabra_objetivo: "bat"
        - id: "a2"
          tipo: "ahorcado"
          definicion: "Objeto que usas para escribir en papel."
          palabra_objetivo: "pen"
    - id: "ruleta-frases"
      tipo: "ruleta_fortuna"
      titulo: "Ruleta de la fortuna"
      descripcion: "Descubre la frase oculta, letra a letra, como en el famoso concurso."
      preguntas:
        - id: "r1"
          tipo: "ruleta_fortuna"
          pista: "Expresión para desear buena suerte."
          frase_objetivo: "Break a leg"
        - id: "r2"
          tipo: "ruleta_fortuna"
          pista: "Frase para animar a alguien a no rendirse."
          frase_objetivo: "Keep going"
    - id: "palabra-desordenada"
      tipo: "palabra_desordenada"
      titulo: "Palabra desordenada"
      descripcion: "Ordena las letras para formar la palabra correcta según la pista."
      preguntas:
        - id: "p1"
          tipo: "palabra_desordenada"
          pista: "Fruta amarilla rica en potasio."
          letras_desordenadas: "NABANA"
          palabra_objetivo: "BANANA"
        - id: "p2"
          tipo: "palabra_desordenada"
          pista: "Lugar donde compras medicinas."
          letras_desordenadas: "YHAPMACR"
          palabra_objetivo: "PHARMACY"
    - id: "memory-parejas"
      tipo: "memory"
      titulo: "Juego de memoria: parejas"
      descripcion: "Encuentra las parejas de palabras en inglés y su traducción."
      preguntas:
        - id: "m1"
          tipo: "memory"
          parejas:
            - ingles: "dog"
              espanol: "perro"
            - ingles: "cat"
              espanol: "gato"
            - ingles: "house"
              espanol: "casa"
```

### Explicación y justificación
- Ahorcado: Refuerza ortografía y vocabulario, fomenta la deducción y la memoria.

- Ruleta de la fortuna: Trabaja frases hechas, expresiones y vocabulario, añadiendo un componente de azar y diversión.

- Palabra desordenada: El usuario debe ordenar letras para formar la palabra correcta. Útil para reforzar ortografía y agilidad mental.

- Memory (parejas): El usuario empareja palabras en inglés con su traducción. Potencia la memoria visual y la asociación de conceptos.

### ¿Por qué son adecuados?
Todos estos modelos son fácilmente adaptables a cualquier dominio (no solo idiomas), fomentan la motivación y el aprendizaje activo, y pueden implementarse como nuevos tipos de preguntas en la arquitectura extensible propuesta en el enunciado.

Además, estos juegos permiten una corrección automática sencilla y ofrecen métricas claras para el seguimiento del progreso del usuario.


### Interfaz para el tipo "memory (parejas)"

La interfaz para el tipo "memory (parejas)" debe ser intuitiva, visual y lúdica, inspirada en los clásicos juegos de memoria.

---

## **Interfaz para "memory (parejas)"**

**Estructura general:**
- Se muestra un tablero con cartas (rectángulos o fichas) dispuestas en filas y columnas, todas boca abajo.
- Cada carta oculta una palabra, imagen, símbolo o concepto.
- El usuario puede hacer clic o tocar dos cartas por turno para descubrir su contenido.

**Flujo de interacción:**
1. **Inicio:**  
   - El tablero aparece con todas las cartas ocultas.
   - Puede haber un contador de intentos y un temporizador opcional para aumentar la motivación.

2. **Selección de cartas:**  
   - El usuario selecciona la primera carta, que se voltea y muestra su contenido (por ejemplo, “dog”).
   - Selecciona una segunda carta, que también se voltea (por ejemplo, “perro”).

3. **Comprobación de pareja:**  
   - Si ambas cartas forman una pareja correcta (por ejemplo, palabra en inglés y su traducción), permanecen descubiertas.
   - Si no coinciden, tras un breve intervalo ambas cartas se vuelven a ocultar automáticamente.

4. **Progreso:**  
   - El proceso se repite hasta que todas las parejas han sido encontradas.
   - Se muestra en pantalla el número de intentos, el tiempo transcurrido y/o la puntuación obtenida.

5. **Finalización:**  
   - Al completar el tablero, se muestra un mensaje de felicitación y las estadísticas de la partida (intentos, tiempo, racha, etc.).

**Elementos visuales y de ayuda:**
- Animaciones suaves al voltear las cartas.
- Posibilidad de incluir pistas opcionales (por ejemplo, “¿Quieres una pista?”).
- Botón para reiniciar el tablero.
- Indicadores visuales de parejas encontradas (por ejemplo, resaltado o animación especial).

**Adaptabilidad:**
- El número de cartas puede variar según la dificultad o el dominio.
- Las cartas pueden contener texto, imágenes, símbolos o incluso sonidos, según el contenido del curso.

---

### **Ejemplo visual simplificado**

```
┌─────┬─────┬─────┬─────┐
│  ?  │  ?  │  ?  │  ?  │
├─────┼─────┼─────┼─────┤
│  ?  │  ?  │  ?  │  ?  │
├─────┼─────┼─────┼─────┤
│  ?  │  ?  │  ?  │  ?  │
└─────┴─────┴─────┴─────┘
```
Al hacer clic, las cartas se voltean mostrando su contenido.

---

### **Ventajas pedagógicas**
- Refuerza la memoria visual y la asociación de conceptos.
- Es aplicable a cualquier dominio (idiomas, historia, ciencias, etc.).
- Fomenta la repetición y el refuerzo positivo a través del juego.

---

Esta interfaz es perfectamente compatible con la filosofía de aplicaciones como Duolingo y con la extensibilidad de tipos de preguntas propuesta en el enunciado[1].

[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/27407806/5840d9f6-67d2-40d4-9801-b110f5513311/enunciado.pdf


# YAML

En YAML no es obligatorio encerrar los campos entre comillas, salvo en situaciones específicas. 
Se pueden escribir los valores sin comillas siempre que no contengan caracteres especiales, espacios al inicio/final, saltos de línea, dos puntos seguidos de un espacio, o símbolos que puedan confundir al parser (como :, #, - en ciertos contextos).

Cuándo usar comillas:

- Si el valor contiene caracteres especiales, saltos de línea o espacios al principio/final.

- Si el texto incluye dos puntos : seguidos de un espacio, o símbolos que puedan ser confundidos con la sintaxis de YAML.

- Para forzar que un número o palabra no se interprete como booleano (por ejemplo, "no" en YAML sin comillas se interpreta como false).

