# Proyectos

## 1. Crear un proyecto de desarrollo

| Qué                                    | Quién                | Por qué                                 |
| -------------------------------------- | -------------------- | --------------------------------------- |
| Se desea poder crear un proyecto nuevo | Gerente de Proyectos | Para poder comenzar un nuevo desarrollo |
**Como Gerente de Proyectos quiero registrar un nuevo proyecto de un cliente para empezar el desarrollo.**

| Pasos                                                                                                                                                                                                                      | Actividades                                     |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------- |
| 1. Ver proyectos existentes<br>2. Ver listado de clientes<br>                                                                                                                                                              | Revisar información previa                      |
| 3. Asignar el cliente<br>4. Proveer un nombre para el proyecto<br>5. Decidir el tipo de proyecto<br>6. Planificar las fechas de inicio y fin estimadas<br>                                                                 | Planificación del proyecto a partir del cliente |
| 7. Asignar un tag<br>8. Asignar un equipo de trabajo y responsables iniciales<br>9. Guardar el proyecto una vez que se han proporcionado los datos necesarios<br>10. Visitar la vista del proyecto con sus características | Planificación interna del proyecto              |

| Pasos                                                 | Sería interesante que...                                                                                                                                                                                                                                   | Variaciones                                                | Excepciones                                                                          | Necesidades diferentes                                                       | Prioridad |
| ----------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------- | ------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------- | --------- |
| Ver proyectos existentes                              | Se pueda ver proyectos existentes con el cliente interesado actual                                                                                                                                                                                         |                                                            |                                                                                      |                                                                              | Could     |
| Ver listado de clientes                               | Se pueda revisar un listado con los clientes                                                                                                                                                                                                               |                                                            |                                                                                      |                                                                              | Could     |
| Asignar el cliente                                    | Se pueda buscar clientes existentes por nombre o email.<br>El sistema me advirtiera si el cliente no existe.<br>Se pueda crear un cliente o al menos asignarle un email perteneciente al cliente de momento.                                               | Múltiples clientes.<br>Cliente es PSA (desarrollo interno) | No se conoce el cliente.                                                             |                                                                              | Should    |
| Proveer un nombre para el proyecto                    | El sistema avise si el nombre ya está en uso o existe uno *muy similar*.                                                                                                                                                                                   |                                                            | Si no tiene nombre todavía, el sistema podría asignarle uno en forma de código único |                                                                              | Could     |
| Decidir el tipo de proyecto                           | Ofrezca las opciones existentes.                                                                                                                                                                                                                           | Por *time and material* o *fixed price*.                   |                                                                                      |                                                                              | Must      |
| Planificar las fechas de inicio y fin estimadas      | El sistema me advirtiera si las fechas no son compatibles.<br>                                                                                                                                                                                             |                                                            |                                                                                      | Proyectos time and material podrían ser menos estrictos con la fecha de fin. | Could     |
| Asignar un tag                                        | Ofrezca las opciones existentes y la posibilidad de crear una nueva.                                                                                                                                                                                       | Tipos de tag                                               | No tiene tags.                                                                       |                                                                              | Must      |
| Asignación de identificador único                     | El sistema asigne un identificador único automáticamente si uno no es provisto por el creador del proyecto. Si el identificador es numérico, use el siguiente número. Si es alfanumérico, puede formularlo a partir del cliente o el título del proyecto |                                                            |                                                                                      |                                                                              | Must      |
| Asignar un equipo de trabajo y responsables iniciales | Se pueda consultar al servicio externo de recursos que usa PSA para elegir los equipos de trabajo iniciales.<br>Se pueda editar luego los recursos y asignar más o menos.                                                                                   |                                                            | No se conocen los equipos disponibles.                                               |                                                                              | Could     |
| Guardar el proyecto                                   | El sistema me advirtiera si alguna característica obligatoria no ha sido completada.<br>El sistema me advirtiera si alguna característica que no sea conveniente dejar en blanco no ha sido completada.                                                   |                                                            |                                                                                      |                                                                              | Must      |
| Visitar la vista del proyecto con sus características |                                                                                                                                                                                                                                                            | Vista de gerente ≠ vista de cliente                        | Se puede querer omitir esta parte del flujo de esta actividad.                       |                                                                              | Could     |

## 2. Visualizar un tablero de proyectos

| Qué                                          | Quién                | Por qué                                          |
|----------------------------------------------|----------------------|--------------------------------------------------|
| Se desea visualizar el estado de los proyectos | Maximiliano Gant     | Para seguir el progreso de manera clara          |

**Como Maximiliano Gant, quiero visualizar un tablero de proyectos que muestre el estado de desarrollo, para que pueda seguir el progreso de manera clara.**

Visualizar un tablero de proyectos

| Pasos                                                                                     | Actividades                   |
|-------------------------------------------------------------------------------------------|-------------------------------|
| 1. Navegar al módulo de proyectos<br>                                                     | Navegación                    |
| 2. Seleccionar la vista de tablero<br>                                                    | Selección de vista            |
| 3. Ver el estado de los proyectos en columnas (inicio, desarrollo, transición)<br>       | Visualización del tablero     |
| 4. Filtrar o buscar proyectos específicos<br>                                             | Filtrado y búsqueda           |
| 5. Mover proyectos entre columnas<br>7. Ver detalles de un proyecto específico<br>    | Interacción con el tablero    |


| Pasos                             | Sería interesante que...                                                                                   | Variaciones                              | Excepciones                                          | Necesidades diferentes                                    | Priority |
|-----------------------------------|------------------------------------------------------------------------------------------------------------|------------------------------------------|------------------------------------------------------|----------------------------------------------------------|----------|
| Navegar al módulo de proyectos    | El sistema resalte el módulo de proyectos si hay proyectos críticos en riesgo.                             |                                          | Usuario sin permisos, mostrar mensaje de error.      |                                                          | Could     |
| Seleccionar la vista de tablero | <br/>Configurar nombre, orden y cantidad de columnas.<br>El sistema permita cambiar entre vista Kanban, lista o calendario. | Vista Kanban, lista o calendario.<br/>Elegir modelo Waterfall (fases secuenciales). ||| Must     |
| Ver el estado de los proyectos    | Las tarjetas muestren información adicional como responsable y fecha estimada al pasar el cursor.         | Personalizar columnas del tablero.       | No hay proyectos en una columna.                     | Proyectos con riesgos podrían resaltarse en rojo.        | Must     |
| Filtrar o buscar proyectos        | El sistema permita filtrar por cliente, responsable o tipo de proyecto, y buscar por nombre o ID.         | Filtrar por múltiples criterios.         | No hay resultados para el filtro/búsqueda.           |                                                          | Should   |
| Mover proyectos entre columnas    | El sistema avise si el proyecto no cumple criterios para cambiar de estado (ej. tareas pendientes).       |                                          | Proyecto bloqueado por dependencias.                 |                                                          | Must     |
| Ver detalles de un proyecto específico        | El sistema permita agregar comentarios o actualizar información directamente desde la vista de detalles.   |                                          |                                                      | Podría haber una vista simplificada para clientes.       | Should   |

---

## 3. Registrar tiempos de proyectos de customización

| Qué                                          | Quién                | Por qué                                          |
|----------------------------------------------|----------------------|--------------------------------------------------|
| Se desea registrar tiempos de análisis, diseño y desarrollo | Maximiliano Gant     | Para controlar el esfuerzo invertido             |

**Como Maximiliano Gant, quiero registrar tiempos de análisis, diseño y desarrollo para proyectos de customización, para que se controle el esfuerzo invertido.**

Registrar tiempos de proyectos de customización

| Pasos                                                                                     | Actividades                   |
|-------------------------------------------------------------------------------------------|-------------------------------|
| 1. Ver proyectos existentes<br>2. Seleccionar un proyecto de customización<br>             | Selección del proyecto        |
| 3. Estimar tiempos iniciales para las fases (análisis, diseño, desarrollo)<br>             | Registro de tiempos           |
| 4. Identificar las fases (análisis, diseño, desarrollo)<br>5. Registrar el tiempo invertido en cada fase | Registro de tiempos           |
| 6. Guardar los datos registrados<br>                                                      | Guardado de datos             |
| 7. Revisar o ajustar los tiempos registrados<br>                                          | Revisión y ajustes            |

| Pasos                             | Sería interesante que...                                                                                   | Variaciones                              | Excepciones                                          | Necesidades diferentes                                    | Priority |
|-----------------------------------|------------------------------------------------------------------------------------------------------------|------------------------------------------|------------------------------------------------------|----------------------------------------------------------|----------|
| Ver proyectos existentes          | Se puedan filtrar proyectos de customización.                                   | Filtrar por múltiples criterios.         | No hay proyectos de customización.       | Mostrar también proyectos de nuevas funcionalidades.     | Should   |
| Seleccionar un proyecto de customización ||||Permitir registrar tiempos de análisis, diseño y desarrollo. | Must     |
| Estimar tiempos iniciales         |                                            | Estimar por fase o por tarea.            | Estimación no proporcionada por el usuario.           |                                                          | Should   |
| Identificar las fases             | El sistema sugiera fases predeterminadas (análisis, diseño, desarrollo).                                   | Incluir fases adicionales (pruebas).     |                                                      |                                                          | Must     |
| Registrar el tiempo invertido     | El sistema permita ingresar tiempo manualmente o usar un temporizador.                                     | Registro por día o por semana.           | Tiempo no ingresado o inválido (negativo).           |      | Must     |
| Guardar los datos registrados     | El sistema confirme el guardado, avise si hay datos incompletos y valide la sumatoria de horas con la estimación total. |                                          |                |                                                          | Must     |
| Revisar o ajustar los tiempos     | El sistema muestre un historial de ajustes y permita ajustar la estimacion.                       |   | Sin permisos para modificar tiempos registrados.     | Revisión por cliente en lugar de gerente.                | Should   |


## 4. Crear una tarea desde un proyecto

| Qué                                                    | Quién               | Por qué                                |
| ------------------------------------------------------ | ------------------- | -------------------------------------- |
| Se desea crear una tarea desde la vista de un proyecto | Gerente de Proyecto | Para organizar el trabajo del proyecto |
**Como Gerente de Proyecto quiero asignar tareas a un proyecto para organizar el trabajo del mismo.**

| Pasos                                                                                                                                                                                                                                 | Actividades                   |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| 1. Ver proyectos existentes<br>2. Elegir un proyecto                                                                                                                                                                                  | Revisar información existente |
| 3. Decidir un nombre<br>4. Asignar recursos a la tarea<br>5. Definir estado de la tarea<br>6. Definir horas estimadas<br>7. Asignar etiquetas a la tarea<br>8. Guardar la tarea una vez que se han proporcionado los datos necesarios | Crear la tarea                |

| Pasos                        | Sería interesante que...                                                                                                                                                                                | Variaciones                                       | Excepciones                            | Necesidades diferentes | Prioridad |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- | -------------------------------------- | ---------------------- | --------- |
| Ver proyectos existentes     | Se pueda ver proyectos existentes con el cliente interesado actual                                                                                                                                      |                                                   | No existen proyectos.                  |                        | Must      |
| Elegir un proyecto           | Se pueda seleccionar el proyecto para el cual voy a crear una tarea                                                                                                                                     |                                                   |                                        |                        | Must      |
| Decidir un nombre            |                                                                                                                                                                                                         |                                                   |                                        |                        | Must      |
| Asignar recursos a la tarea  | El sistema ofrezca los recursos existentes y su disponibilidad.<br>                                                                                                                                     |                                                   | No existen recursos. (se asume que sí) |                        | Could     |
| Definir estado de la tarea   | El sistema ofrezca las opciones posibles.<br>El sistema asigne "PENDIENTE" o un estado similar de forma predeterminada y solo se cambie si el usuario lo desea                                          | Ejemplos: "PENDIENTE", "EN PROCESO", "FINALIZADA" |                                        |                        | Must      |
| Definir horas estimadas      |                                                                                                                                                                                                         |                                                   |                                        |                        | Must      |
| Asignar etiquetas a la tarea | Ofrezca las opciones existentes y la posibilidad de crear una nueva.                                                                                                                                    | Tipos de tag. Ejemplos: "BDD", "Docker", "DevOps" |                                        |                        | Must      |
| Guardar la tarea             | El sistema me advirtiera si alguna característica obligatoria no ha sido completada.<br>El sistema me advirtiera si alguna característica que no sea conveniente dejar en blanco no ha sido completada. |                                                   |                                        |                        |           |



## 5. Asociar riesgos a proyectos con planes de mitigación y contingencia

| Qué                                                               | Quién              | Por qué                                                                 |
|-------------------------------------------------------------------|--------------------|-------------------------------------------------------------------------|
| Asociar riesgos a los proyectos con planes de mitigación y contingencia | Maximiliano Gant   | Para que se gestione adecuadamente cualquier problema                   |

**Como Maximiliano Gant, quiero asociar riesgos a cada proyecto con planes de mitigación y contingencia, para que se gestione adecuadamente cualquier problema.**

### Asociar riesgos a proyectos

| Pasos                                                                                      | Actividades                         |
|--------------------------------------------------------------------------------------------|-------------------------------------|
| 1. Ingresar al detalle de un proyecto                                                      | Navegación                          |
| 2. Acceder a la sección de gestión de riesgos                                              | Visualización de módulo             |
| 3. Registrar un nuevo riesgo identificado                                                  | Registro de información             |
| 4. Ingresar descripción, categoría, impacto y probabilidad                                 | Carga de datos                      |
| 5. Adjuntar plan de mitigación                                                             | Gestión de mitigación               |
| 6. Adjuntar plan de contingencia                                                           | Gestión de contingencia             |
| 7. Guardar y visualizar todos los riesgos registrados                                      | Confirmación y visualización        |
| 8. Editar o eliminar riesgos existentes                                                    | Edición y mantenimiento             |

| Pasos                             | Sería interesante que...                                                                 | Variaciones                                           | Excepciones                                       | Necesidades diferentes                                     | Priority |
|-----------------------------------|------------------------------------------------------------------------------------------|-------------------------------------------------------|---------------------------------------------------|-------------------------------------------------------------|----------|
| Ingresar al detalle de un proyecto| Se pueda acceder rápidamente desde el tablero o listado de proyectos.                   | Acceso desde diferentes vistas                        | Proyecto inexistente o sin permisos               | Acceso simplificado para perfiles de seguimiento.          | Must     |
| Acceder a la sección de riesgos   | El sistema notifique si hay riesgos críticos sin plan de mitigación.                    | Vista de riesgos por estado o criticidad              | No existen riesgos cargados aún                   |                                                             | Should   |
| Registrar un nuevo riesgo         | Se pueda duplicar un riesgo existente para acelerar carga.                              | Crear desde cero o duplicar                          | Falta de campos obligatorios                      |                                                             | Could    |
| Ingresar descripción y detalles   | Los campos tengan ejemplos o ayudas contextuales.                                       | Campos de texto, listas desplegables, adjuntos       | Validación de campos vacíos                       | Riesgos generales que se heredan entre proyectos.          | Must     |
| Adjuntar plan de mitigación       | Se sugieran estrategias comunes según el tipo de riesgo.                                | Texto libre o plantilla guiada                       | Riesgo no requiere mitigación (bajo impacto)      |                                                             | Should   |
| Adjuntar plan de contingencia     | Permita vincular tareas o hitos del proyecto como parte de la contingencia.             | Relación con otras partes del sistema                | Contingencia aún no definida                      |                                                             | Should   |
| Guardar y visualizar riesgos      | Se permita ordenar y filtrar por criticidad, impacto o estado.                          | Tabla de riesgos, indicadores visuales               | Ningún riesgo guardado                            | Poder exportar los riesgos en PDF/Excel para reuniones.    | Must     |
| Editar o eliminar riesgos         | El sistema mantenga historial de cambios por auditoría.                                 | Registro de versiones de cada riesgo                 | Usuario sin permisos de edición                   |                                                             | Must     |

---

## 6. Seguimiento de horas trabajadas contra tareas

| Qué                                          | Quién                | Por qué                                          |
|----------------------------------------------|----------------------|--------------------------------------------------|
| Se desea implementar un mecanismo para el seguimiento de horas trabajadas contra tareas | Maximiliano Gant     | Para garantizar la precisión en los costos       |

**Como Maximiliano Gant, quiero implementar un mecanismo para el seguimiento de horas trabajadas contra tareas, para que se garantice la precisión en los costos.**

Seguimiento de horas trabajadas contra tareas

| Pasos                                                                                     | Actividades                   |
|-------------------------------------------------------------------------------------------|-------------------------------|
| 1. Seleccionar un proyecto<br>2. Seleccionar una tarea específica del proyecto<br>        | Selección del proyecto y tarea |
| 3. Registrar las horas trabajadas en la tarea<br>                                         | Registro de horas             |
| 4. Guardar los datos registrados<br>                                                      | Guardado de datos             |
| 5. Revisar o ajustar las horas registradas<br>                                            | Revisión y ajustes            |
| 6. Generar un reporte de horas trabajadas para validar costos<br>                         | Validación de costos          |

| Pasos                             | Sería interesante que...                                                                                   | Variaciones                              | Excepciones                                          | Necesidades diferentes                                    | Priority |
|-----------------------------------|------------------------------------------------------------------------------------------------------------|------------------------------------------|------------------------------------------------------|----------------------------------------------------------|----------|
| Seleccionar un proyecto || Filtrar por cliente o responsable.       | No hay proyectos disponibles.                        | Podría seleccionarse desde un tablero Kanban.            | Must     |
| Seleccionar una tarea específica  | El sistema muestre las tareas asociadas al proyecto con su estado (iniciada, no iniciada, terminada).      | Filtrar tareas por estado o responsable. | No hay tareas asociadas al proyecto.                 | Seleccionar múltiples tareas para registrar en lote.     | Must     |
| Registrar las horas trabajadas    | El sistema permita registrar horas manualmente.                                 | Registro por día o por semana.           | Horas no ingresadas o inválidas (negativas).         | Registrar horas por equipo en lugar de individual.       | Must     |
| Guardar los datos registrados     | El sistema valide que las horas registradas sumen correctamente con la estimación total del proyecto.      |                                          ||                                                          | Must     |
| Revisar o ajustar las horas       | El sistema muestre un historial de cambios y permita ajustes por tarea con justificación.                  | Ajuste automático por desviación.        | Sin permisos para modificar horas registradas.       | Revisión por cliente en lugar de gerente.                | Should   |
| Generar un reporte de horas       | El sistema permita exportar el reporte en formato PDF o CSV para facturación.                              | Reporte por proyecto o por cliente.      || Incluir costos asociados a servicios en la nube.         | Should   |



## 7. Visualizar tareas de un proyecto

| Qué                                                     | Quién                       | Por qué                                                                 |
| ------------------------------------------------------- | --------------------------- | ----------------------------------------------------------------------- |
| Se desea registrar visualizar las tareas de un proyecto | Cualquier miembro de PMTool | Para controlar el estado, la distribución de trabajo, tiempo y recursos |

| Pasos                                                                                                                                                                     | Actividades             |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------- |
| 1. Elegir un proyecto                                                                                                                                                     | Seleccionar el proyecto |
| 2. Ver tareas en forma de listado<br>3. Filtrar y ordenar tareas por estado, fecha fin, etiquetas<br>4. Buscar tareas por nombre<br>5. Buscar tareas por recurso asignado | Visualizar la tarea     |

| Pasos                                                     | Sería interesante que...                                                                                                                                                                                      | Variaciones                                  | Excepciones                 | Necesidades diferentes | Prioridad |
| --------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- | --------------------------- | ---------------------- | --------- |
| Elegir un proyecto                                        |                                                                                                                                                                                                               |                                              | No existen proyectos.       |                        | Must      |
| Ver tareas en forma de listado                            | Se pueda visualizar las tareas en una lista con su nombre, estado y demás atributos separados por columnas                                                                                                    |                                              |                             |                        | Must      |
| Filtrar y ordenar tareas por estado, fecha fin, etiquetas | El sistema ordene alfanuméricamente y por fechas los campos que se decidan ordenar. <br>El sistema permita filtrar por estado, fecha fin y etiquetas para visualizar unicamente un subconjunto de las tareas. |                                              |                             |                        | Must      |
| Buscar tareas por nombre                                  | El sistema permita filtrar tareas por nombre en un barra de búsqueda<br>                                                                                                                                      |                                              |                             |                        | Must      |
| Buscar tareas por recurso asignado                        | El sistema permita filtrar tareas por recursos asignados (trabajadores) en un barra de búsqueda                                                                                                               | Nombre específico o "Sin recursos asignados" | No tiene recursos asignados |                        | Could     |



