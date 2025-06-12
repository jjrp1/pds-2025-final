---  
title: Apuntes de pds-gui 
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-12  
modified: 2025-06-12  
author: "Juanjo Ruiz"  
---  
 
# 🎨 Diseño de la Interfaz Gráfica de Usuario 

Para el desarrollo de la Interfaz Gráfica de Usuario (en adelante GUI), utilizaremos JavaFX

📌 Utilizaremos **FXML** para diseñar las vistas y **Controladores** para gestionar eventos.



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

  

### Pantallas iniciales:
1. **Menú Principal**  
   - Iniciar curso  
   - Ver mis cursos  
   - Importar nuevo curso  
   - Ver estadísticas  

2. **Selección de estrategia de aprendizaje**  
   - Secuencial  
   - Aleatorio  
   - Repetición espaciada  

3. **Vista de pregunta/pantalla de aprendizaje**  
   - Mostrar contenido según tipo de pregunta  
   - Botones de siguiente/anterior  
   - Guardar progreso  

4. **Visualización de estadísticas**  
   - Tiempo total  
   - Mejor racha  
   - Última sesión  

