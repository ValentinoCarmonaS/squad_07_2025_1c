# Visión del Módulo de Gestión Unificada de Proyectos

## Usuarios  
- **Internos:**  
  - Gerencia de Operaciones: Planificación y seguimiento de proyectos.  
  - Equipos de Desarrollo e Implementaciones: Registro de tareas y customizaciones.  
  - Soporte: Gestión de tickets e incidentes.  
  - Finanzas: Reportes de costos, facturación y SLAs.  
- **Externos:** Clientes que puedan cargar tickets y, eventualmente, gestionar sus proyectos.

## Funcionalidades Principales  
| Primera version (MVP)                                                                          | Segunda version                                                                 |
| ---------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| **Manejo** de Proyectos.                                                                       | Manejo de **riesgos**: registro de cada riesgo y su evolución en cada proyecto. |
| Visibilizar en tabla o panel tipo **Kanban**.                                                  | Reportes                                                                        |
| Administración de **Tareas**.                                                                  | **Alertas**.                                                                    |
| Visibilizar **estado de proyectos atrasados** (Soluzzia)                                       | Permisos para usuarios                                                          |
| **Integración** con módulos relacionados. Por ejemplo con finanzas para cargar horas a tareas. |                                                                                 |
| Todo usuario puede hacer todo (no hay permisos)                                                |                                                                                 |
## Integración con otros módulos
### Asignación de recursos
El sistema consume información de quienes son los recursos disponibles de un servicio externo (API).

### Listado de proyectos
Soporte y otras areas que lo requieran deben poder consultar el listado de proyectos existentes y algunos datos de ellos mediante una API.

## Interfaz
- Se podrá entrar a cualquier módulo.
- Listado de Proyectos
- Vista de proyecto con tareas
- Tareas ordenadas por tablero tipo Kanban

#### Listado de proyectos
Al entrar a **proyectos**, se podría ver el todos los proyectos enlistados con información sobre cada uno de ellos (nombre, tipo, etc). Se debería poder filtrarlos, eliminiarlos y *entrar* a cada uno de ellos. 

#### Vista de proyecto
Cada proyecto debe tener tareas y poder visualizarlas de al menos dos formas es crucial:
1. Listado convencional
2. Tabla tipo **kanban**
