-- ========================================
-- ÍNDICES PARA PROYECTOS
-- ========================================
CREATE INDEX idx_projects_client_id ON projects(client_id);
CREATE INDEX idx_projects_type ON projects(type);
CREATE INDEX idx_projects_status ON projects(status);
CREATE INDEX idx_projects_leader_id ON projects(leader_id);
CREATE INDEX idx_projects_dates ON projects(start_date, end_date);

-- ========================================
-- ÍNDICES PARA TAREAS
-- ========================================
CREATE INDEX idx_tasks_project_id ON tasks(project_id);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_assigned_resource ON tasks(assigned_resource_id);
CREATE INDEX idx_tasks_due_date ON tasks(due_date);

-- ========================================
-- ÍNDICES PARA TAGS
-- ========================================
CREATE INDEX idx_project_tags_project_id ON project_tags(project_id);
CREATE INDEX idx_project_tags_name ON project_tags(tag_name);
CREATE INDEX idx_task_tags_task_id ON task_tags(task_id);
CREATE INDEX idx_task_tags_name ON task_tags(tag_name);

-- ========================================
-- ÍNDICES PARA TASK_TICKETS
-- ========================================
CREATE INDEX idx_task_tickets_task_id ON task_tickets(task_id);
CREATE INDEX idx_task_tickets_ticket_id ON task_tickets(ticket_id);