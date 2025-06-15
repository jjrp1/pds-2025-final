# Interfaz de Usuario - PDS

## Descripción General

La interfaz de usuario de PDS está diseñada para proporcionar una experiencia de aprendizaje clara y enfocada, minimizando distracciones y maximizando la efectividad del estudio.

## Paneles Principales

### 1. Panel de Selección de Curso
- Lista de cursos disponibles
- Botones de acción (inicialmente deshabilitados hasta seleccionar un curso):
  - Inspeccionar: Siempre habilitado, muestra información detallada del curso
  - Comenzar: Inicia una nueva sesión
  - Reanudar: Continúa una sesión guardada
  - Flashcards: Accede al modo de tarjetas de memoria
  - Estadísticas: Muestra el progreso y rendimiento

### 2. Panel de Selección de Estrategia
- Opciones de estrategia de aprendizaje:
  - Secuencial: Preguntas en orden
  - Aleatoria: Preguntas en orden aleatorio
  - Repetición espaciada: Optimizada para retención
- Botones:
  - Confirmar: Inicia la sesión con la estrategia seleccionada
  - Cancelar: Vuelve al panel de selección de curso

### 3. Panel de Preguntas
- Indicador de progreso:
  - Número de pregunta actual
  - Total de preguntas
  - Barra de progreso visual
- Contenedor de pregunta:
  - Enunciado
  - Opciones de respuesta (según tipo de pregunta)
- Botón de verificación:
  - Valida la respuesta
  - Muestra feedback (correcto/incorrecto)
  - Muestra respuesta correcta si es necesario
  - Avanza automáticamente a la siguiente pregunta
- Botón de salir:
  - Guarda el progreso
  - Permite reanudar la sesión más tarde

## Flujo de Interacción

1. **Selección de Curso**
   - Usuario selecciona un curso de la lista
   - Los botones de acción se habilitan
   - El estado muestra el curso seleccionado

2. **Inicio de Sesión**
   - Usuario hace clic en "Comenzar"
   - Selecciona una estrategia de aprendizaje
   - Confirma la selección

3. **Resolución de Preguntas**
   - Sistema muestra una pregunta
   - Usuario selecciona/responde
   - Usuario verifica su respuesta
   - Sistema proporciona feedback
   - Sistema avanza automáticamente

4. **Finalización**
   - Al completar todas las preguntas:
     - Muestra resumen de la sesión
     - Opción de salir
   - Durante la sesión:
     - Opción de salir en cualquier momento
     - Progreso guardado automáticamente

## Consideraciones de Diseño

### Progresión Lineal
- No se permite navegación libre entre preguntas
- Fuerza una progresión ordenada
- Mejora la efectividad del aprendizaje

### Feedback Inmediato
- Validación instantánea de respuestas
- Muestra de respuestas correctas
- Progreso visual constante

### Persistencia
- Guardado automático del progreso
- Capacidad de reanudar sesiones
- Historial de rendimiento

## Mejoras Futuras

1. **Resumen de Sesión**
   - Total de preguntas respondidas
   - Porcentaje de aciertos
   - Tiempo total de estudio
   - Áreas de mejora identificadas

2. **Modo Pausa**
   - Pausar sesión en cualquier momento
   - Guardar progreso automáticamente
   - Reanudar desde el punto exacto

3. **Personalización**
   - Ajustes de interfaz
   - Preferencias de visualización
   - Configuración de feedback 