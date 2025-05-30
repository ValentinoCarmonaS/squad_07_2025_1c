-- Tabla para usuarios (autenticación JWT)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL -- Ejemplo: ADMIN, USER
);

-- Tabla para proyectos
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    client_id INT, -- Referencia a un cliente (puede ser NULL si no hay relación inmediata)
    type VARCHAR(50) NOT NULL, -- Ejemplo: TIME_AND_MATERIAL, FIXED_PRICE
    start_date DATE NOT NULL,
    end_date DATE,
    estimated_hours INT,
    tags VARCHAR(255), -- ¿Debe ser: ("tag1,tag2") o ("tag1" y "tag2")?
    status VARCHAR(50) NOT NULL -- Ejemplo: INITIATED, IN_PROGRESS, COMPLETED
);

-- Tabla para tareas
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    project_id INT NOT NULL, -- Referencia al proyecto al que pertenece la tarea
    name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL, -- Ejemplo: TO_DO, IN_PROGRESS, DONE
    estimated_hours INT,
    actual_hours INT,
    tags VARCHAR(255), -- La misma duda que en projects
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);