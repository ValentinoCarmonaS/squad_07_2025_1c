# API PSA - Modulo de Proyectos

## Tecnologías
- Backend: Java 17, Spring Boot 3.5.0, Spring Data JPA, Spring Security, Flyway, PostgreSQL.
- Documentación API: Springdoc OpenAPI (Swagger UI).
- Gestión de Variables: dotenv-java para cargar configuraciones desde .env.
- Testing: JUnit, Cucumber para pruebas de integración.
- Gestión de Dependencias: Maven.
- Arquitectura: Microservicios con API REST.

---

## Requisitos para Ejecutar Localmente
### Prerrequisitos
- Java 17 (JDK instalado y configurado).
- Maven (puedes usar el wrapper ./mvnw incluido en el proyecto).
- PostgreSQL (base de datos en la nube con Neon, configurada en .env).
- Git (para clonar el repositorio).
- Archivo .env con las variables de entorno necesarias (ver .env.example).

### Configuración Inicial
1. Clona el Repositorio:
```bash
git clone https://github.com/ValentinoCarmonaS/squad_07_2025_1c.git
cd proyecto-api
```

2. Configura el Archivo .env:
- Copia `.env.example` a `.env`:
```bash
cp .env.example .env
```
- Edita `.env` con las credenciales de la base de datos Neon y otras configuraciones:
```bash
# Configuración de la base de datos Postgresql usando variables de entorno
PGHOST=tu_host
PGDATABASE=tu_db
PGUSER=tu_usuario
PGPASSWORD=tu_contraseña

# Configuración de Hibernate/JPA
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true

# Configuración de Flyway
SPRING_FLYWAY_ENABLED=true

# Configuración de servidor
PORT=8080
```

3. Instala Dependencias:
```bash
make install
```

## Ejecución en Local
1. Inicia la Aplicación en Modo Desarrollo:
```bash
make dev
```
Esto limpia el proyecto, instalara las dependencias y ejecuta `mvn spring-boot:run`. La aplicación estará disponible en http://localhost:8080 (o el puerto definido en `.env`).

2. Accede a la Documentación de la API:
> Abre este [link](https://squad-07-2025-1c.onrender.com/swagger-ui/index.html) para explorar los endpoints disponibles con Swagger UI.
> 
> Tambien todos los documentos finales del proyecto se encuentran en este [link](https://drive.google.com/file/d/1e22B1wmQyQSw3FzNEy5DdK-x8szU7Pth/view?usp=drive_link).

## Estructura del Proyecto
```bash
proyecto-api/
├──src/
│  ├── main/c
│  │   ├── java/com/psa/proyectos/
│  │   │   ├── config/            # Configuraciones globales
│  │   │   ├── controller/        # Controladores REST
│  │   │   ├── service/           # Lógica de negocio (interfaces + impl)
│  │   │   ├── repository/        # Spring Data JPA
│  │   │   ├── model/             # Entidades JPA
│  │   │   ├── dto/               # Objetos de Transferencia (Request/Response)
│  │   │   ├── exception/         # Manejo de excepciones globales
│  │   │   └── ProyectosApplication.java # Main class
│  │   ├── resources/
│  │   │   ├── application.properties    # Config principal
│  │   │   └── db/                # Migraciones (Flyway)
│  ├── test/
│  │   ├── java/com/psa/proyectos/
│  │   │   └── features/          # Pruebas Cucumber (.feature + Steps)
│  │   └── resources/
├── pom.xml
├── Makefile
├── README.md
├── .env                        # Variables de entorno (NO committear)
└── .env.example                # Variables de entorno de ejemplo (SI committear)
```
