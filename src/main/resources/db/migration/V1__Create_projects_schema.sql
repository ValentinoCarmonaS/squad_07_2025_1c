-- Tabla para proyectos
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    client_id INT, -- ID del cliente del sistema externo (NO es FK)
    type VARCHAR(50) NOT NULL CHECK (type IN ('DEVELOPMENT', 'IMPLEMENTATION')),
    start_date DATE NOT NULL,
    end_date DATE,
    estimated_hours INT CHECK (estimated_hours > 0),
    status VARCHAR(50) NOT NULL DEFAULT 'INITIATED' CHECK (status IN ('INITIATED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    leader_id INT, -- ID del líder del proyecto del sistema externo de recursos (NO es FK)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT chk_project_dates CHECK (end_date IS NULL OR end_date >= start_date)
);