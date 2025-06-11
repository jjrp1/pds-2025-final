---  
title:  Estadísticas de sesión
subtitle:  
description:  
keywords:  
status: desarrollo  
created: 2025-06-11  
modified: 2025-06-11  
author: "Juanjo Ruiz"  
---  
 
# Estadísticas de sesión

### **Básicas (a implementar):**
- **Fecha de comienzo**
- **Última revisión**
- **Tiempo acumulado** (total y por bloque)
- **Tiempo medio** (por bloque y por pregunta)
- **Estrategia elegida** (secuencial, aleatoria, repetición espaciada, etc.)
- **Bloque y pregunta en curso** (para reanudar)
- **Porcentaje de completitud** (preguntas respondidas / total)
- **Tasa de aciertos hasta el momento** (nº aciertos / nº preguntas respondidas)
- **Para cada pregunta:**
  - Resultado (acierto, fallo, sin contestar)
  - Tiempo dedicado (si es cero, no contestada y la sesión se quedó ahí)

### **Adicionales (mejora):**

- **Racha de aciertos/fallos** (por ejemplo, cuántas preguntas seguidas acertó o falló)
- **Mejor racha de aciertos** dentro de la sesión
- **Preguntas marcadas “difíciles”** (si existe la opción)


## **Resumen gráfico (ejemplo de estructura por sesión):**

```yaml
sesion:
  fecha_inicio: "2025-06-10T15:00:00"
  fecha_ultima_revision: "2025-06-11T18:30:00"
  tiempo_total: 00:45:32
  estrategia: "aleatoria"
  bloque_actual: 3
  pregunta_actual: 2
  porcentaje_completado: 70
  tasa_aciertos: 85
  mejor_racha_aciertos: 12
  preguntas:
    - id: 1
      resultado: "acierto"
      tiempo: 00:00:12
      intentos: 1
      pistas_usadas: 0
    - id: 2
      resultado: "fallo"
      tiempo: 00:00:21
      intentos: 2
      pistas_usadas: 1
    - id: 3
      resultado: "sin_contestar"
      tiempo: 0
      intentos: 0
      pistas_usadas: 0
```
