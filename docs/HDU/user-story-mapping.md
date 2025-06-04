# Epicas PSA

Las siguientes son épicas agrupadas en dos temas:

- **Proyectos**
    1. Crear un proyecto de desarrollo
    2. Visualizar lista de proyectos
    3. Visualizar tablero Kanban de proyectos
    4. Visualizar todos los detalles de un proyecto
    5. Modificar un proyecto
- **Tareas**
	6. Crear una tarea desde un proyecto 
	7. Visualizar una lista de tareas de un proyecto 
	8. Visualizar todos los detalles de una tarea 
	9. Modificar una tarea 
	10. Seguimiento de horas trabajadas contra tareas

---

# Tema: Proyectos

Las USM se han pensado y actualizado conforme al nuevo alcance de proyecto discutido hasta la reunión de minuta-07-po.

## 1. Crear un proyecto de desarrollo

|Qué|Quién|Por qué|
|---|---|---|
|Se desea poder crear un proyecto nuevo|Gerente de Proyectos|Para poder comenzar un nuevo desarrollo|

**Como Gerente de Proyectos quiero registrar un nuevo proyecto de un cliente para empezar el desarrollo.**

|Pasos|Actividades|
|---|---|
|1. Ver proyectos existentes<br>|Revisar información previa|
|3. Asignar el cliente<br>4. Proveer un nombre para el proyecto<br>5. Decidir el tipo de proyecto<br>6. Planificar las fechas de inicio y fin estimadas<br>|Planificación del proyecto a partir del cliente|
|7. Asignar un tag<br>8. Asignar un equipo de trabajo y responsables iniciales<br>9. Guardar el proyecto una vez que se han proporcionado los datos necesarios<br>10. Visitar la vista del proyecto con sus características|Planificación interna del proyecto|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Prioridad|
|---|---|---|---|---|---|
|Ver proyectos existentes|Se pueda ver proyectos existentes con el cliente interesado actual||||Could|
|Asignar el cliente|Se pueda buscar clientes existentes por nombre o email.<br>El sistema me advirtiera si el cliente no existe.|Múltiples clientes.<br>Cliente es PSA (desarrollo interno)|No se conoce el cliente.||Should|
|Proveer un nombre para el proyecto|El sistema avise si el nombre ya está en uso.||Si no tiene nombre todavía, el sistema podría asignarle uno en forma de código único||Could|
|Decidir el tipo de proyecto|Ofrezca las opciones existentes.|Por _time and material_ o _fixed price_.|||Must|
|Planificar las fechas de inicio y fin estimadas|El sistema me advirtiera si las fechas no son compatibles.<br>|||Proyectos time and material permiten no especificar fecha fin|Could|
|Asignar un tag _(opcional)_|Ofrezca las opciones existentes y la posibilidad de crear una nueva.|Tipos de tag|No tiene tags.||Must|
|Asignación de identificador único|El sistema asigne un identificador único automáticamente si uno no es provisto por el creador del proyecto. Si el identificador es numérico, use el siguiente número. Si es alfanumérico, puede formularlo a partir del cliente o el título del proyecto||||Must|
|Guardar el proyecto|El sistema me advirtiera si alguna característica obligatoria no ha sido completada.||||Must|
|Visitar la vista del proyecto con sus características _(opcional)_|||Se puede querer omitir esta parte del flujo de esta actividad.||Could|

---

## 2. Visualizar lista de proyectos

|Qué|Quién|Por qué|
|---|---|---|
|Se desea visualizar los proyectos en formato lista|Maximiliano Gant|Para revisar información detallada de múltiples proyectos|

**Como Maximiliano Gant, quiero acceder a una lista de proyectos que muestre sus detalles, para revisar información detallada de múltiples proyectos.**

|Pasos|Actividades|
|---|---|
|1. Navegar al módulo de proyectos<br>|Navegación|
|2. Seleccionar vista de lista<br>|Selección de vista|
|3. Ver proyectos en formato tabla con columnas para cada campo/atributo<br>4. Filtrar o buscar proyectos específicos<br>5. Ordenar por diferentes columnas<br>|Visualizar|
|6. Ver detalles de un proyecto específico<br>|Interacción con la lista|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Priority|
|---|---|---|---|---|---|
|Navegar al módulo de proyectos<br>|||||Could|
|Seleccionar vista de lista<br>|El sistema permita pasar al modo Kanban|Kanban o lista.|||Must|
|Ver proyectos en formato tabla con columnas para cada campo/atributo|Se puedan ver nombre, cliente, tipo, fechas, estado||||Must|
|Filtrar o buscar proyectos específicos|El sistema permita filtrar por cliente, responsable o tipo de proyecto, y buscar por nombre o ID.|Filtrar por múltiples criterios.|No hay resultados para el filtro/búsqueda.||Should|
|Ordenar por diferentes columnas|El sistema permita ordenar por cualquier columna de forma ascendente y descendente.||||Must|
|Ver detalles de un proyecto específico|El sistema permita agregar comentarios o actualizar información directamente desde la vista de detalles.|||Podría haber una vista simplificada para clientes.|Should|

---

## 3. Visualizar tablero Kanban de proyectos

|Qué|Quién|Por qué|
|---|---|---|
|Se desea visualizar el estado de los proyectos en un tablero Kanban|Maximiliano Gant|Para seguir el progreso visual de los proyectos|

**Como Maximiliano Gant, quiero acceder a un tablero Kanban de proyectos que muestre su estado de desarrollo, para que pueda seguir el progreso de manera visual e intuitiva.**

|Pasos|Actividades|
|---|---|
|1. Navegar al módulo de proyectos<br>|Navegación|
|2. Seleccionar vista de tablero Kanban<br>|Selección de vista|
|3. Ver proyectos en columnas por estado (inicio, desarrollo, transición)<br>|Visualización del tablero|
|4. Filtrar o buscar proyectos específicos<br>|Filtrado y búsqueda|
|5. Mover proyectos entre columnas<br>6. Ver detalles de un proyecto específico<br>|Interacción con el tablero|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Priority|
|---|---|---|---|---|---|
|Navegar al módulo de proyectos|||||Could|
|Seleccionar vista de tablero Kanban|El sistema permita pasar al modo lista.|Kanban o lista.|||Must|
|Ver proyectos en formato Kanban con columnas|Hayan columnas por cada estado del proyecto.<br>Se muestren tarjetas en cada columna indicando nombre, tipo y cliente.<br>|Columnas personalizables.|No hay proyectos en una columna.||Must|
|Crear columna nueva|El sistema permita crear nuevas columnas populables al antojo del usuario.||||Should|
|Mover proyectos entre columnas|El sistema avise si el proyecto no cumple criterios para cambiar de estado (ej. tareas pendientes).||Proyecto bloqueado por estado incompatible.||Must|
|Ver detalles de un proyecto específico|El sistema permita agregar comentarios o actualizar información directamente desde la vista de detalles.|||Podría haber una vista simplificada para clientes.|Should|

---

## 4. Visualizar todos los detalles de un proyecto

|Qué|Quién|Por qué|
|---|---|---|
|Se desea visualizar en forma de panel de control los atributos de un proyecto|Maximiliano Gant|Para controlarlo a detalle.|

**Como Maximiliano Gant, quiero acceder a un proyecto de forma tal que se muestre a detalle todos sus atributos, para que pueda seguir su evolución y estado.**

|Pasos|Actividades|
|---|---|
|1. Navegar al módulo de proyectos<br>2. Elegir un proyecto|Navegación|
|3. Ver atributos del proyecto en forma de panel de control<br>4. Ver tareas|Visualización de atributos|
|5. Modificar o eliminar|Modificación|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Priority|
|---|---|---|---|---|---|
|Navegar al módulo de proyectos<br>|||||Must|
|Elegir un proyecto<br>|||||Must|
|Ver atributos del proyecto en forma de panel de control<br>|El sistema provea mayor detalle que la lista de proyectos||||Must|
|Ver tareas|||||Must|
|Modificar o eliminar|El sistema permita la edición de distintos atributos del proyecto, incluyendo tareas.||||Should|

---

## 5. Modificar un proyecto

|Qué|Quién|Por qué|
|---|---|---|
|Se desea modificar un proyecto|Gerente de Proyectos|Para impactar los cambios de necesidades del cliente|

**Como Gerente de Proyectos quiero modificar el proyecto para cumplir con los cambios de necesidades del cliente.**

|Pasos|Actividades|
|---|---|
|1. Ver proyectos existentes<br>2. Elegir un proyecto|Revisar información existente|
|3. Modificar estado actual<br>4. Crear tareas para el proyecto<br>5. Eliminar proyecto<br>6. Guardar los cambios|Cambiar los detalles de un proyecto|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Prioridad|
|---|---|---|---|---|---|
|Ver proyectos existentes|||No existen proyectos.||Must|
|Elegir un proyecto|||||Must|
|Modificar estado actual<br>|El sistema avise si el proyecto no cumple criterios para cambiar de estado (ej. tareas pendientes).|"NO INICIADO", "EN DESARROLLO", "FINALIZADO"|||Must|
|Crear tareas para el proyecto|||||Must|
|Eliminar proyecto<br>|El sistema pida confirmar la acción||||Should|
|Guardar los cambios|El sistema valide que todos los campos obligatorios estén completos.||||Must|
|Visitar la vista del proyecto con sus características _(opcional)_|||Se puede querer omitir esta parte del flujo de esta actividad.||Could|

---

# Tema: Tareas

## 6. Crear una tarea desde un proyecto

|Qué|Quién|Por qué|
|---|---|---|
|Se desea crear una tarea para un proyecto dado|Gerente de Proyectos|Para organizar el trabajo del proyecto|

**Como Gerente de Proyectos quiero asignar tareas a un proyecto para organizar el trabajo del mismo.**

|Pasos|Actividades|
|---|---|
|1. Ver proyectos existentes<br>2. Elegir un proyecto|Revisar información existente|
|3. Decidir un nombre<br>4. Asignar recurso a la tarea<br>5. Definir horas estimadas<br>6. Asignar etiquetas a la tarea<br>7. Definir estado de la tarea<br>8. Guardar la tarea una vez que se han proporcionado los datos necesarios|Crear la tarea|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Prioridad|
|---|---|---|---|---|---|
|Ver proyectos existentes|Se pueda ver proyectos existentes con el cliente interesado actual||No existen proyectos.||Must|
|Elegir un proyecto|Se pueda seleccionar el proyecto para el cual voy a crear una tarea||||Must|
|Decidir un nombre|||||Must|
|Asignar recurso a la tarea _(opcional)_|El sistema ofrezca los recursos existentes y su disponibilidad.<br>||||Could|
|Definir horas estimadas|||||Must|
|Asignar etiquetas a la tarea|Ofrezca las opciones existentes y la posibilidad de crear una nueva.|Tipos de tag. Ejemplos: "BDD", "Docker", "DevOps"|||Must|
|Definir estado de la tarea|El sistema asigne "NO INICIADA" o un estado similar de forma predeterminada|"NO INICIADA", "EN PROCESO", "FINALIZADA"|||Must|
|Guardar la tarea|El sistema me advirtiera si alguna característica obligatoria no ha sido completada.||||Must|
|Visitar la vista de la tarea con sus características _(opcional)_|||Se puede querer omitir esta parte del flujo de esta actividad.||Could|

---

## 7. Visualizar una lista de tareas de un proyecto

|Qué|Quién|Por qué|
|---|---|---|
|Se desea visualizar las tareas de un proyecto|Cualquier miembro de PMTool|Para controlar el estado, la distribución de trabajo, tiempo y recursos|

**Como miembro del equipo, quiero visualizar las tareas de un proyecto para controlar el estado, la distribución de trabajo, tiempo y recursos.**

|Pasos|Actividades|
|---|---|
|1. Elegir un proyecto|Seleccionar el proyecto|
|2. Ver tareas en forma de listado<br>3. Filtrar y ordenar tareas por estado, fecha fin, etiquetas<br>4. Buscar tareas por nombre<br>|Visualizar las tareas|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Prioridad|
|---|---|---|---|---|---|
|Elegir un proyecto|||No existen proyectos.||Must|
|Ver tareas en forma de listado|Se pueda visualizar las tareas en una lista con su nombre, estado y demás atributos separados por columnas||||Must|
|Filtrar y ordenar tareas por estado, fecha fin, etiquetas|El sistema ordene alfanuméricamente y por fechas los campos que se decidan ordenar. <br>El sistema permita filtrar por estado, fecha fin y etiquetas para visualizar únicamente un subconjunto de las tareas.||||Must|
|Buscar tareas por nombre|El sistema permita filtrar tareas por nombre en una barra de búsqueda<br>||||Could|

---

## 8. Visualizar todos los detalles de una tarea

|Qué|Quién|Por qué|
|---|---|---|
|Se desea visualizar en forma de panel de control los atributos de una tarea|Maximiliano Gant|Para controlarlo a detalle.|

**Como Maximiliano Gant, quiero acceder a una tarea de forma tal que se muestre a detalle todos sus atributos, para que pueda seguir su evolución y estado.**

|Pasos|Actividades|
|---|---|
|1. Elegir una tarea|Navegación|
|2. Ver atributos de la tarea en forma de panel de control|Visualización de atributos|
|3. Modificar o eliminar|Modificación|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Priority|
|---|---|---|---|---|---|
|Elegir una tarea<br>|||||Must|
|Ver atributos de la tarea en forma de panel de control<br>|El sistema provea mayor detalle que la lista de tareas||||Must|
|Modificar o eliminar|El sistema permita la edición de distintos atributos de la tarea.||||Should|

---

## 9. Modificar una tarea

|Qué|Quién|Por qué|
|---|---|---|
|Se desea modificar una tarea para un proyecto dado|Gerente de Proyectos|Para impactar en cambios de necesidades del proyecto|

**Como Gerente de Proyectos quiero modificar tareas para cumplir con los cambios de necesidades del proyecto.**

|Pasos|Actividades|
|---|---|
|1. Ver tareas existentes<br>2. Elegir una tarea|Revisar información existente|
|3. Modificar horas estimadas<br>4. Modificar estado actual<br>5. Modificar recurso asignado<br>6. Eliminar tarea<br>7. Guardar los cambios|Cambiar los detalles de una tarea|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Prioridad|
|---|---|---|---|---|---|
|Ver tareas existentes|||No existen tareas.||Must|
|Elegir una tarea|||||Must|
|Modificar horas estimadas<br>|||No admita horas negativas||Must|
|Modificar estado actual<br>||"NO INICIADA", "EN PROCESO", "FINALIZADA"|||Must|
|Modificar recurso asignado|El sistema ofrezca en formato lista los recursos disponibles||||Must|
|Eliminar tarea<br>|El sistema pida confirmar la acción||||Should|
|Guardar los cambios|El sistema valide que todos los campos obligatorios estén completos.||||Must|
|Visitar la vista de la tarea con sus características _(opcional)_|||Se puede querer omitir esta parte del flujo de esta actividad.||Could|

---

## 10. Seguimiento de horas trabajadas contra tareas

|Qué|Quién|Por qué|
|---|---|---|
|Se desea implementar un mecanismo para el seguimiento de horas trabajadas contra tareas|Maximiliano Gant|Para garantizar la precisión en los costos|

**Como Maximiliano Gant, quiero implementar un mecanismo para el seguimiento de horas trabajadas contra tareas, para que se garantice la precisión en los costos.**

|Pasos|Actividades|
|---|---|
|1. Seleccionar un proyecto<br>2. Seleccionar una tarea específica del proyecto<br>|Selección del proyecto y tarea|
|3. Registrar las horas trabajadas en la tarea<br>|Registro de horas|
|4. Guardar los datos registrados<br>|Guardado de datos|
|5. Generar un reporte de horas trabajadas para validar costos<br>|Validación de costos|

|Pasos|Sería interesante que...|Variaciones|Excepciones|Necesidades diferentes|Priority|
|---|---|---|---|---|---|
|Seleccionar un proyecto||Filtrar por cliente o responsable.|No hay proyectos disponibles.|Podría seleccionarse desde un tablero Kanban.|Must|
|Seleccionar una tarea específica|El sistema muestre las tareas asociadas al proyecto con su estado||||Must|
|Registrar las horas trabajadas|El sistema permita registrar horas manualmente.||Horas no ingresadas o inválidas (negativas).|Registrar horas por equipo en lugar de individual.|Must|
|Guardar los datos registrados|El sistema actualice los cambios en la estimación total del proyecto.||||Must|
|Generar un reporte de horas|El sistema permita exportar el reporte en formato PDF o CSV para facturación.|Reporte por proyecto o por cliente.|||Could|

---
