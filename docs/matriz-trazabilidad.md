# Matriz de Trazabilidad

| ID | ID | Requerimiento | Tipo | Interesado/s | Minuta | Módulo | Prioridad (MoSCoW) | US | Épica |
|----|----|--------------|----|-------------|--------|--------|-------------------|----|-------|
| 1 | 1 | Consultar información de Proyectos desde Soporte y visceversa para mantener un sistema unificado | RNF | CEO | minuta-01-ceo-psa | General | Must | - | - |
| 1 | 2 | La herramienta debe ser adaptable para clientes externos | RNF | CEO | minuta-01-ceo-psa | Ventas | Should | - | - |
| 1 | 3 | Proporcionar una interfaz de comunicación bajo el protocolo REST para facilitar el intercambio de información entre los distintos módulos y productos. | RF | Tomas Bruneleschi | minuta-06-po-psa | General | Must | - | - |
| 1 | 4 | Consultar al módulo externo que contiene a la pool de desarrolladores disponibles a la hora de asignar recursos | RF | Tomas Bruneleschi | minuta-06-po-psa | General | Could | - | - |
| _ | _ | _ | _ | _ | _ | _ | _ | _ | _ |
| 2 | 1 | Permitir crear proyectos básicos con nombre, cliente, tipo, financiación y fechas estimadas | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-06** | 1 |
| 2 | 1.1 | El nombre de un proyecto debe ser único | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-06** | 1 |
| 2 | 1.2 | El tipo de proyecto puede ser de IMPLEMENTACIÓN o DESARROLLO | RF | Gustavo Cuchina | minuta-05-area-implmentaciones-psa | Implementaciones | Should | **US-06** | 1 |
| 2 | 1.3 | El tipo de financiación puede ser "FIXED PRICE" o "TIME AND MATERIAL" | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-06** | 1 |
| 2 | 1.3.1 | Cuando se crea un proyecto "TIME AND MATERIAL" se permite omitir la fecha de fin estimada | RF | Maximiliano Gant | minuta-07-po-psa | Proyectos | Must | **US-07** | 1 |
| 2 | 1.3.2 | Cuando se registre un cambio de alcance en un proyecto "FIXED PRICE" seguir un proceso de control, permitiendo re-estimar costos y tiempos para aprobación del cliente. | RF | Ricardo Ratio | minuta-02-gerente-finanzas-psa | Proyectos | Won't | - | 10 |
| 2 | 1.4 | Todo proyecto tiene fecha de inicio y de fin estimadas | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-07** | 1 |
| 2 | 1.5 | Todo proyecto tiene un identificador único que no es el nombre | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-06** | 1 |
| 2 | 1.6 | Todo proyecto tiene un cliente externo o interno (PSA) | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-06** | 1 |
| 2 | 1.7 | Todo proyecto tiene un líder de proyecto (recurso de PSA) | RF | Maximiliano Gant | reunión iteración 5 junio | Proyectos | Must | **US-06** | 1 |
| 2 | 2 | Todo proyecto admite la asignación de etiquetas | RF | Gustavo Cuchina | minuta-05-area-implmentaciones-psa | Implementaciones | Must | **US-08** | 1 |
| 2 | 3 | Todo proyecto tiene estado (NO INICIADO, EN DESARROLLO y FINALIZADO) | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-17** | 3 |
| 2 | 4 | Todo proyecto podrá cambiar de estado según el estado de sus tareas | RF | - | - | Proyectos | Should | **US-17** | 5 |
| 2 | 4.1 | Cuando un proyecto tenga al menos una actividad "INICIADA" podrá cambiar de "INICIO" a "EN DESARROLLO" | RF | - | - | Proyectos | Should | **US-17** | 5 |
| 2 | 4.2 | Cuando un proyecto tenga todas las actividades en estado "FINALIZADA" podrá cambiar de "TRANSICION" a "FINALIZADO" | RF | - | - | Proyectos | Should | **US-17** | 5 |
| 2 | 5 | Todo proyecto puede tener tareas para organizar el trabajo en unidades manejables | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Should | **US-19** | 6 |
| 2 | 5.1 | A través de las tareas del proyecto se deben poder saber los recursos asociados al proyecto | RF | Tomas Bruneleschi | minuta-07-po-psa | Proyectos | Must | **US-20** | 6 |
| 2 | 5.2 | A través de las tareas del proyecto se deben poder calcular las horas estimadas para finalizar el proyecto como la sumatoria de horas de sus tareas | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-31** | 8 y 11 |
| 2 | 6 | Todo proyecto debe permitir registrar el tiempo real invertido. | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Won't | - | 10 |
| 2 | 7 | Registrar detalladamente las configuraciones, customizaciones y versión de los proyectos realizados para cada cliente. | RF | Agusto Aguanti y Gustavo Cuchina | minuta-05-area-implmentaciones-psa | Implementaciones | Could | - | - |
| 2 | 8 | Almacenar la documentación de análisis, requerimientos y resultados de las implementaciones. | RF | Gustavo Cuchina | minuta-05-area-implmentaciones-psa | Implementaciones | Won't | - | - |
| 2 | 9 | Proporcionar una tabla tipo lista con información detallada de todos los proyectos | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-09** | 2 |
| 2 | 9.1 | Cuando se visualicen los proyectos en forma de tabla, los encabezados de las columnas de la lista son "nombre, tipo, cliente, estado, presupuesto" | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-09** | 2 |
| 2 | 9.2 | Cuando se visualicen los proyectos en forma de tabla, se debe poder filtrar los proyectos de acuerdo a distintos criterios: "tipo, estado, tags" | RF | Tomas Bruneleschi | minuta-06-po-psa | Proyectos | Must | **US-11** | 2 y 3 |
| 2 | 9.3 | Cuando se visualicen los proyectos en forma de tabla, se debe poder buscar proyectos por nombre | RF | - | - | Proyectos | Must | **US-05** | 2 |
| 2 | 9.4 | Cuando se visualicen los proyectos en forma de tabla, se debe poder ordenar por diferentes columnas | RF | - | - | Proyectos | Should | **US-12** | 2 |
| 2 | 10 | Proporcionar un tablero tipo kanban con información básica de todos los proyectos y sus estados | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-13** | 3 |
| 2 | 10.1 | Cuando se visualicen los proyectos en forma de kanban, las columnas estándar serán "NO INICIADO", "EN DESARROLLO", "FINALIZADO" | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-13** | 3 |
| 2 | 10.2 | Cuando se visualicen los proyectos en forma de kanban, se debe poder agregar las columnas del tablero según las necesidades de cada equipo y/o proyecto | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Won't | - | 3 |
| 2 | 10.3 | Cuando se visualicen los proyectos en forma de kanban, se debe poder visualizar en forma de tarjetas cada proyecto | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-13** | 3 |
| 2 | 11 | Cuando se seleccione un proyecto en cualquier tipo de visualización, se debe poder ingresar a otra vista con todos los detalles y tareas que contiene | RF | Tomas Bruneleschi | minuta-06-po-psa | Proyectos | Must | **US-16** | 2 y 3 |
| 2 | 12 | Todo proyecto debe poder eliminarse | RF | Tomas Bruneleschi | minuta-06-po-psa | Proyectos | Must | **US-18** | 5 |
| 3 | 1 | Permitir crear tareas básicas con nombre, horas estimadas y recurso asignado (opcional) | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-19** | 6 |
| 3 | 1.1 | Las horas estimadas pueden modificarse | RF | Maximiliano Gant | minuta-07-po-psa | Proyectos | Must | **US-25** | 9 |
| 3 | 1.2 | Cada tarea tiene a lo sumo un recurso asociado. | RF | Tomas Bruneleschi | minuta-07-po-psa | Proyectos | Must | **US-20** | 6 |
| 3 | 2 | Toda tarea tiene estado (POR HACER, EN PROGRESO, HECHA) | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-26** | 6 |
| 3 | 2.1 | Toda tarea podrá cambiar su estado de manera progresiva y sentido creciente | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Must | **US-26** | 6 |
| 3 | 3 | Proporcionar una tabla tipo lista con información detallada de las tareas de un proyecto | RF | Tomas Bruneleschi | minuta-06-po-psa | Proyectos | Must | **US-21** | 7 |
| 3 | 4 | Cuando se seleccione una tarea, se debe poder ingresar a otra vista con todos los detalles que contiene | RF | Tomas Bruneleschi | reunión prototipo | Proyectos | Must | **US-24** | 7 |
| 3 | 5 | Toda tarea debe poder eliminarse | RF | Tomas Bruneleschi | varias | Proyectos | Must | **US-24** | 7 |
| 3 | 6 | Toda tarea permite modificar su recurso asociado | RF | Tomas Bruneleschi | minuta-07-po-psa | Proyectos | Must | **US-20** | 6 |
| 3 | 7 | Cuando se modifiquen las horas estimadas de una tarea se debe poder calcular automáticamente el total de horas del proyecto | RF | Maximiliano Gant | minuta-04-gerencia-operaciones-psa | Proyectos | Should | **US-31** | 10 |
| 4 | 1 | Cuando se desea asignar etiquetas a un proyecto o tarea pueden ser creadas o seleccionadas entre las existentes | RF | Gustavo Cuchina | minuta-05-area-implmentaciones-psa | Implementaciones | Must | **US-34** | 1 |
| 4 | 2 | Las etiquetas son representadas a través de un nombre único | RF | Tomas Bruneleschi | - | Proyectos | Must | **US-34** | 1 |