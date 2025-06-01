-- Tabla para tareas
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    project_id INT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'TO_DO' CHECK (status IN ('TO_DO', 'IN_PROGRESS', 'DONE', 'CANCELLED')),
    estimated_hours INT CHECK (estimated_hours > 0),
    assigned_resource_id INT, -- ID del recurso asignado del sistema externo (NO es FK)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date DATE
);

-- Tabla para relacionar tareas con tickets del módulo de soporte
CREATE TABLE task_tickets (
    id SERIAL PRIMARY KEY,
    task_id INT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    ticket_id INT NOT NULL, -- ID del ticket del microservicio de soporte (NO es FK)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Evitar duplicados
    UNIQUE(task_id, ticket_id)
);