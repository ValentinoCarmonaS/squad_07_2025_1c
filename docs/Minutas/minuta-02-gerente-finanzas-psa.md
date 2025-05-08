# Minuta de la Reunión - 10 de abril de 2025

## **Información General**

- **Nombre de la reunión**: Entrevista para recolección de requisitos del nuevo software
- **Fecha**: 10/04/2025
- **Hora de inicio - fin**: 20:41 - 21:20
- **Lugar**: Google Meet

## **Asistentes**

- **Presentes**:
  - Ricardo Ratio (Gerente de Finanzas, PSA)
  - Equipo de desarrollo (responsables del diseño del software)
- **Invitados ausentes**: Ninguno

## **Objetivo de la reunión**

Recolectar los requisitos clave del Gerente de Finanzas para el desarrollo de un nuevo software de gestión de proyectos que optimice los procesos financieros en PSA.

## **Glosario**
- PSA: Nombre de la empresa
- CRM: Customer Relationship Management (sistema en uso actual)
- ERP: Enterprise Resource Planning (sistema en uso actual)
- _time and material_: Tipo de contrato donde se cobra por el tiempo y materiales utilizados en el proyecto.
- _fixed priced_ o "llave en mano": Tipo de contrato donde se cobra un precio fijo por el proyecto, independientemente del tiempo y materiales utilizados.
- subfacturacion: Cuando se declara un valor de una mercancia por debajo de su precio real.
- Acuerdos de nivel de servicio: contrato formal que define los estándares de calidad y desempeño que un proveedor de servicios se compromete a cumplir con un cliente. En particular, finanzas se concentra en el tiempo en vivo.

## **Temas tratados**

Se discutieron los siguientes puntos principales:

- **Facturación de proyectos**: Se destacó la importancia de facturar correctamente los proyectos "Time and Material", registrando las horas trabajadas por proyecto y permitiendo marcar el cierre de ciclos de facturación. Sin embargo, el la emisión de facturas sigue siendo exclusivamente propia del ERP
- **Registro de horas**: Los empleados deben poder registrar sus horas diaria o semanalmente.
- **Integración con herramientas existentes**: El software debe integrarse con la gestión de clientes (CRM) y el procesamiento de facturas (ERP), vinculando facturas a proyectos específicos.
- **Exportación de datos**: Se requiere exportar información a Microsoft Project para compartirla con externos.
- **Costos no laborales**: Posibilidad de asignar costos *extra* como servidores o capacitaciones a proyectos mediante facturas.

### Acuerdos de nivel de servicio
Cada proyecto tiene una prioridad (hay cuatro niveles) según el tiempo de respuesta que soporte debe darle ante una **caida del sistema**.
- **Acuerdos de nivel de servicio (SLAs)**: Se solicitaron alertas automáticas cuando un incidente alcance el 50% del tiempo permitido, para evitar incumplimientos y multas.
- Se requiere saber de antemano de cuánto es la multa si no se cumple el acuerdo.

### Proyectos "Time and Material"
La mayoría de proyectos tienen esta modalidad. 
- Ante cualquier cambio o modificación que requiera más tiempo de desarollo, el presupuesto se ajusta automáticamente.

###  Proyectos "Fixed Price"

- Los cambios de alcance deben seguir un proceso de control, re-estimando costos y tiempos para aprobación del cliente.

### Acceso a datos
- Se necesita de datos en tiempo real, no solo en reportes periódicos.
- Se necesita poder filtrar datos segun tipo de proyecto, equipo, empleado.
- Se necesita poder mostrar el registro de horas por:
	- proyecto,
	- empleado,
	- intervalo de tiempo $[a,b]$,
	- equipo.
- **Equipos**: Se requiere poder consultar las horas insumidas y controlar si un equipo esta cargando horas y trabajando.
- Se necesita poder consultar las facturas en ERP
- Se requiere poder importar datos de Microsoft Projects.
### **Acuerdos y compromisos**

- El equipo de desarrollo se compromete a incluir los requisitos mencionados en el diseño del software.
- Se priorizará la integración con herramientas existentes y el acceso a datos en tiempo real.

### **Pendientes para próximas reuniones**

- Definir cómo se gestionarán los cambios de alcance en proyectos "Fixed Price" dentro del software.
- Validar las integraciones propuestas con las herramientas existentes.

## **Próxima reunión**

- **Fecha propuesta**: 24/04/2025
- **Hora**: 18:30
- **Temas a tratar**: Actividades de descubrimiento con usuarios de PSA Stand Up Meeting squads con Program Manager, Product Owner