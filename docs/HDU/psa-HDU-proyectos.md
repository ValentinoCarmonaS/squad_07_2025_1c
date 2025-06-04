# User Stories - PSA

## Tema: Proyectos

### US-01: Crear proyecto

**Como** Gerente de Proyectos  
**Quiero** crear un nuevo proyecto con información básica (nombre, cliente, tipo, financiación)  
**Para que** pueda comenzar un nuevo desarrollo

**Criterios de Aceptación:**

- Puedo asignar un cliente existente o marcarlo como interno (PSA)
- Puedo definir tipo de proyecto (Desarollo o Implementación)
- Puedo definir financiación de proyecto (Time & Material o Fixed Price)
- Puedo proveer un nombre único para el proyecto
- El sistema asigna automáticamente un identificador único
- El sistema valida que todos los campos obligatorios estén completos

**Prioridad:** Must

---

### US-02: Planificar fechas de proyecto

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

### US-03: Etiquetar proyectos

**Como** Gerente de Proyectos  
**Quiero** asignar etiquetas (tags) a un proyecto  
**Para que** pueda clasificar, describir y filtrar proyectos eficientemente

**Criterios de Aceptación:**

- Puedo seleccionar etiquetas existentes
- Puedo crear nuevas etiquetas
- Puedo guardar el proyecto sin etiquetas (opcional)

**Prioridad:** Must

---

### US-04: Ver lista de proyectos

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

### US-05: Buscar proyectos

**Como** usuario del sistema  
**Quiero** buscar proyectos por diferentes criterios
**Para que** pueda encontrar proyectos específicos rápidamente

**Criterios de Aceptación:**

- Puedo buscar por nombre de proyecto
- El sistema muestra mensaje cuando no hay resultados

**Prioridad:** Must

---
### US-06: Filtrar proyectos

**Como** usuario del sistema  
**Quiero** filtrar proyectos por diferentes criterios  
**Para que** pueda encontrar proyectos específicos rápidamente

**Criterios de Aceptación:**

- Puedo filtrar por tipo, estado, tags
- El sistema muestra mensaje cuando no hay resultados

**Prioridad:** Could

---

### US-07: Ordenar lista de proyectos

**Como** usuario del sistema  
**Quiero** ordenar la lista de proyectos por diferentes columnas  
**Para que** pueda organizar la información según mis necesidades

**Criterios de Aceptación:**

- Puedo ordenar por cualquier columna visible
- Puedo alternar entre orden ascendente y descendente
- El ordenamiento funciona para fechas y contenido alfanumérico

**Prioridad:** Should

---

### US-08: Ver tablero Kanban de proyectos

**Como** Maximiliano Gant  
**Quiero** visualizar proyectos en un tablero Kanban  
**Para que** pueda seguir el progreso visual de los proyectos

**Criterios de Aceptación:**

- Veo columnas por estado (No Iniciado, En Desarrollo, Finalizado)
- Veo tarjetas con nombre, tipo y cliente de cada proyecto
- Puedo alternar entre vista Kanban y vista lista

**Prioridad:** Must

---

### US-09: Personalizar columnas Kanban

**Como** usuario del sistema  
**Quiero** crear y personalizar columnas del tablero Kanban  
**Para que** pueda adaptar el tablero a las necesidades de mi equipo

**Criterios de Aceptación:**

- Puedo crear nuevas columnas personalizables además de las columnas estándar
- Las columnas no poseen restricción alguna respecto al estado del proyecto
- Puedo eliminar columnas personalizadas (no las estándar)

**Prioridad:** Must

---

### US-10: Mover proyectos en Kanban

**Como** usuario del sistema  
**Quiero** mover proyectos entre columnas del tablero Kanban  
**Para que** pueda actualizar el estado de los proyectos visualmente

**Criterios de Aceptación:**

- Puedo arrastrar y soltar proyectos entre columnas
- El sistema valida las reglas de transición de estados entre columnas estándar
- El sistema solo permite movimientos válidos
- El estado del proyecto se actualiza al moverlo

**Prioridad:** Should

---

### US-11: Ver detalles completos de proyecto

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

### US-12: Modificar estado de proyecto

**Como** Gerente de Proyectos  
**Quiero** cambiar el estado de un proyecto  
**Para que** pueda reflejar el progreso actual del desarrollo

**Criterios de Aceptación:**

- Puedo cambiar entre estados: No Iniciado, En Desarrollo, Finalizado
- El sistema valida las reglas de transición de estados
- El sistema advierte si hay tareas pendientes que impiden el cambio
- Los cambios se guardan correctamente

**Prioridad:** Must

---

### US-13: Eliminar proyecto

**Como** Gerente de Proyectos  
**Quiero** eliminar un proyecto  
**Para que** pueda remover proyectos cancelados o completados

**Criterios de Aceptación:**

- El sistema solicita confirmación antes de eliminar

**Prioridad:** Must

---

## Tema: Tareas

### US-14: Crear tarea básica

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

**Prioridad:** Must

---

### US-15: Asignar recurso a tarea

**Como** Gerente de Proyectos  
**Quiero** asignar un recurso a una tarea  
**Para que** pueda distribuir la carga de trabajo del equipo

**Criterios de Aceptación:**

- Puedo seleccionar un recurso de la lista disponible *al crear o modificar la tarea*
- Cada tarea tiene máximo un recurso asignado
- Puedo dejar la tarea sin recurso asignado (opcional)
- El sistema muestra los recursos disponibles

**Prioridad:** Could

---

### US-16: Ver lista de tareas de proyecto

**Como** miembro del equipo  
**Quiero** visualizar las tareas de un proyecto en formato lista  
**Para que** pueda controlar el estado y distribución del trabajo

**Criterios de Aceptación:**

- Veo columnas con nombre, estado, recurso asignado, horas estimadas, etiquetas
- Puedo ver todas las tareas del proyecto seleccionado
- La lista se actualiza cuando se agregan/modifican tareas
- El sistema muestra mensaje cuando no hay tareas

**Prioridad:** Must

---

### US-17: Ordenar tareas

**Como** usuario del sistema  
**Quiero** filtrar y ordenar las tareas de un proyecto  
**Para que** pueda encontrar tareas específicas rápidamente

**Criterios de Aceptación:**

- Puedo filtrar por estado, etiquetas
- Puedo ordenar alfabéticamente y por fechas (ascendente/descendente)
- Puedo buscar tareas por nombre
- El sistema mantiene los filtros aplicados hasta que los cambie

**Prioridad:** Should

---

### US-18: Buscar tareas por nombre

**Como** usuario del sistema  
**Quiero** buscar tareas por nombre usando una barra de búsqueda  
**Para que** pueda encontrar tareas específicas rápidamente

**Criterios de Aceptación:**

- Puedo escribir en una barra de búsqueda
- El sistema muestra mensaje cuando no hay resultados

**Prioridad:** Could

---

### US-19: Ver detalles completos de tarea

**Como** Maximiliano Gant  
**Quiero** acceder a una vista detallada de una tarea  
**Para que** pueda ver todos sus atributos y gestionar su progreso

**Criterios de Aceptación:**

- Veo todos los atributos de la tarea (más detalle que la lista)
- Puedo acceder a funciones de edición desde esta vista
- Veo información del proyecto asociado

**Prioridad:** Must

---

### US-20: Modificar horas estimadas de tarea

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

### US-21: Cambiar estado de tarea

**Como** miembro del equipo  
**Quiero** actualizar el estado de una tarea  
**Para que** pueda reflejar el progreso del trabajo

**Criterios de Aceptación:**

- Puedo cambiar entre estados: No Iniciada, En Proceso, Finalizada
- El sistema permite transiciones válidas entre estados

**Prioridad:** Must

---

### US-22: Modificar recurso de tarea

**Como** Gerente de Proyectos  
**Quiero** cambiar el recurso asignado a una tarea  
**Para que** pueda redistribuir la carga de trabajo según necesidades

**Criterios de Aceptación:**

- Puedo seleccionar un nuevo recurso de la lista disponible
- Puedo quitar la asignación de recurso (dejar sin asignar)

**Prioridad:** Must

---

### US-23: Eliminar tarea

**Como** Gerente de Proyectos  
**Quiero** eliminar una tarea de un proyecto  
**Para que** pueda remover tareas canceladas o incorrectas

**Criterios de Aceptación:**

- El sistema solicita confirmación antes de eliminar
- Puedo cancelar la operación de eliminación
- La tarea se elimina permanentemente tras confirmar
- El total de horas del proyecto se actualiza automáticamente

**Prioridad:** Must

---

### US-24: Registrar horas trabajadas

**Como** miembro del equipo  
**Quiero** registrar las horas que trabajé en una tarea específica  
**Para que** se pueda hacer seguimiento preciso del tiempo invertido

**Criterios de Aceptación:**

- Puedo seleccionar un proyecto y una tarea específica
- Puedo ingresar horas trabajadas manualmente
- El sistema valida que sean valores positivos

**Prioridad:** Must

---

### US-25: Generar reporte de horas

**Como** Maximiliano Gant  
**Quiero** generar reportes de horas trabajadas  
**Para que** pueda validar costos y analizar el tiempo invertido

**Criterios de Aceptación:**

- Puedo generar reportes por proyecto o por cliente
- Puedo exportar el reporte en formato PDF o CSV
- El reporte incluye información relevante para facturación
- El sistema genera el reporte basado en datos actuales

**Prioridad:** Could

---
