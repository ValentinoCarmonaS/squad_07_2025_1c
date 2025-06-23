# User Stories - PSA

## Tema: Proyectos

### US-06: Crear proyecto

**Como** Gerente de Proyectos  
**Quiero** crear un nuevo proyecto con información básica (nombre, cliente, lider de proyecto, tipo, tipo de financiación, fechas estimadas)  
**Para que** pueda comenzar un nuevo desarrollo

**Criterios de Aceptación:**

- Puedo asignar un cliente existente o marcarlo como interno (PSA)
- Puedo asignar un líder de proyecto
- Puedo definir tipo de proyecto (Desarrollo o Implementación)
- Puedo definir tipo de financiación de proyecto (Time & Material o Fixed Price)
- Puedo definir fecha de inicio
- Puedo definir fecha de fin (opcional para proyectos Time & Material)
- Puedo proveer un nombre único para el proyecto
- El sistema asigna automáticamente un identificador único
- El sistema valida que todos los campos obligatorios estén completos

**Prioridad:** Must

---

### US-07: Planificar fecha de fin de un proyecto

**Como** Gerente de Proyectos  
**Quiero** establecer fechas de inicio y fin estimadas para un proyecto  
**Para que** pueda planificar el cronograma de desarrollo

**Criterios de Aceptación:**

- Puedo definir fecha de inicio estimada
- Puedo definir fecha de fin (opcional para proyectos Time & Material)
- El sistema valida que fecha inicio sea anterior a fecha fin cuando ambas están definidas
- El sistema advierte si las fechas no son compatibles

**Prioridad:** Must

---

### US-08: Etiquetar proyectos

**Como** Gerente de Proyectos  
**Quiero** asignar etiquetas (tags) a un proyecto  
**Para que** pueda clasificar, describir proyectos eficientemente

**Criterios de Aceptación:**

- Puedo crear nuevas etiquetas
- Puedo guardar el proyecto sin etiquetas (opcional)
- Puedo remover o agregar etiquetas a un proyecto existente

**Prioridad:** Must

---

### US-09: Ver lista de proyectos

**Como** Maximiliano Gant  
**Quiero** visualizar proyectos en formato tabla  
**Para que** pueda revisar información detallada de múltiples proyectos

**Criterios de Aceptación:**

- Veo columnas con nombre, cliente, tipo, fechas, estado
- Puedo alternar entre vista lista y vista Kanban
- Puedo acceder a detalles de un proyecto específico
- La lista muestra mensaje apropiado cuando no hay proyectos

**Prioridad:** Must

---
### US-11: Filtrar proyectos

**Como** usuario del sistema  
**Quiero** filtrar un proyecto  
**Para que** pueda encontrar proyectos específicos rápidamente

**Criterios de Aceptación:**

- Puedo filtrar por nombre, tipo, estado, tag (singular)
- Puedo buscar proyectos por nombre
- Puedo combinar filtros y busqueda
- El sistema mantiene los filtros aplicados hasta que los cambie

**Prioridad:** Should

---

### US-12: Ordenar lista de proyectos [exclusivo front-end]

**Como** usuario del sistema  
**Quiero** ordenar la lista de proyectos por diferentes columnas  
**Para que** pueda organizar la información según mis necesidades

**Criterios de Aceptación:**

- Puedo ordenar por cualquier columna visible
- Puedo alternar entre orden ascendente y descendente
- El ordenamiento funciona para fechas y contenido alfanumérico

**Prioridad:** Should

---

### US-13: Ver tablero Kanban de proyectos [exclusivo front-end]

**Como** Maximiliano Gant  
**Quiero** visualizar proyectos en un tablero Kanban  
**Para que** pueda seguir el progreso visual de los proyectos

**Criterios de Aceptación:**

- Veo columnas por estado (No Iniciado, En Desarrollo, Finalizado)
- Veo tarjetas con nombre, tipo y cliente de cada proyecto
- Puedo alternar entre vista Kanban y vista lista

**Prioridad:** Must

---

### US-16: Ver detalles completos de proyecto

**Como** Gerente de Proyectos
**Quiero** acceder a una vista detallada de un proyecto  
**Para que** pueda ver todos sus atributos y tareas asociadas

**Criterios de Aceptación:**

- Veo todos los atributos del proyecto (más detalle que la lista)
- Veo las tareas asociadas al proyecto
- Puedo acceder a funciones de edición desde esta vista
- Puedo navegar a tareas específicas

**Prioridad:** Must

---

### US-17: Modificar estado de proyecto

Como Gerente de Proyectos
Quiero actualizraa el estado del proyecto según el progreso de sus tareas
Para que pueda evitar discrepancias de estado entre un proyecto y sus tareas

**Criterios de Aceptación:**

- Puedo cambiar entre estados: INICIADO, EN PROGRESO, EN TRANSICIÓN en base al estado de sus tareas
- Si no tiene tareas o están todas POR HACER, el proyecto estará INICIADO
- Si hay una tarea EN PROGRESO, el proyecto estará EN PROGRESO
- Si cada una de sus tareas está HECHA, el proyecto estará EN TRANSICIÓN

**Prioridad:** Must


---

### US-18: Eliminar proyecto

**Como** Gerente de Proyectos  
**Quiero** eliminar un proyecto  
**Para que** pueda remover proyectos cancelados o completados

**Criterios de Aceptación:**

- El sistema solicita confirmación antes de eliminar
- Se eliminan todas las tareas asociadas al mismo

**Prioridad:** Must

---

## Tema: Tareas

### US-19: Crear tarea básica

**Como** Gerente de Proyectos  
**Quiero** crear una tarea dentro de un proyecto  
**Para que** pueda organizar el trabajo en unidades manejables

**Criterios de Aceptación:**

- Puedo definir un nombre para la tarea dentro del proyecto
- Puedo definir horas estimadas (valores positivos)
- Puedo asignar etiquetas existentes o crear nuevas
- Puedo asignar un recurso para la tarea (opcional)
- El estado inicial es "No Iniciada" por defecto
- El sistema valida campos obligatorios antes de guardar
- El sistema actualiza **automáticamente** el total de horas del proyecto

**Prioridad:** Must

---

### US-20: Asignar recurso a tarea

**Como** Gerente de Proyectos  
**Quiero** asignar un recurso a una tarea  
**Para que** pueda distribuir la carga de trabajo del equipo

**Criterios de Aceptación:**

- Puedo seleccionar un recurso de la lista disponible *al crear o modificar la tarea*
- Cada tarea tiene máximo un recurso asignado
- Puedo dejar la tarea sin recurso asignado (opcional)
- El sistema muestra los recursos disponibles

**Prioridad:** Must

---

### US-21: Ver lista de tareas de proyecto

**Como** miembro del equipo  
**Quiero** visualizar las tareas de un proyecto en formato lista  
**Para que** pueda controlar el estado y distribución del trabajo

**Criterios de Aceptación:**

- Veo columnas con nombre, estado, recurso asignado, horas estimadas, etiquetas
- Puedo ver todas las tareas del proyecto seleccionado
- La lista se actualiza cuando se agregan/modifican tareas


**Prioridad:** Must

---

### US-24: Ver detalles completos de tarea

**Como** Maximiliano Gant  
**Quiero** acceder a una vista detallada de una tarea  
**Para que** pueda ver todos sus atributos y gestionar su progreso

**Criterios de Aceptación:**

- Veo todos los atributos de la tarea (más detalle que la lista)
- Puedo acceder a funciones de edición desde esta vista
- Veo información del proyecto asociado

**Prioridad:** Must

---

### US-25: Modificar horas estimadas de tarea

**Como** Gerente de Proyectos  
**Quiero** cambiar las horas estimadas de una tarea  
**Para que** pueda ajustar las estimaciones según el progreso real

**Criterios de Aceptación:**

- Puedo ingresar nuevas horas estimadas
- El sistema valida que sean valores positivos (no negativos)
- El sistema actualiza **automáticamente** el total de horas del proyecto
- Los cambios se guardan correctamente

**Prioridad:** Must

---

### US-26: Cambiar estado de tarea

**Como** miembro del equipo  
**Quiero** actualizar el estado de una tarea  
**Para que** pueda reflejar el progreso del trabajo

**Criterios de Aceptación:**

- Puedo cambiar entre estados: No Iniciada, En Proceso, Finalizada
- El sistema permite transiciones válidas entre estados

**Prioridad:** Must

---

## Tema: Etiquetas

### US-34: Asignar etiqueta

**Como** Gerente de Proyectos  
**Quiero** asignar una etiqueta a un proyecto o tarea  
**Para que** pueda categorizar proyectos y tareas

**Criterios de Aceptación:**

- Puedo seleccionar crear una nueva
- La etiqueta es unica dentro del proyecto si es una etiqueta de proyecto
- La etiqueta es unica dentro de la tarea si es una etiqueta de tarea

**Prioridad:** Must

---

### US-31: Calcular duración estimada de proyecto

**Como** Gerente de Proyectos  
**Quiero** calcular la duración estimada de un proyecto en base a sus tareas  
**Para que** pueda controlar el tiempo estimado de un proyecto

**Criterios de Aceptación:**

- Puedo calcular la duración estimada de un proyecto en base a la suma de las horas estimadas de sus tareas

**Prioridad:** Must