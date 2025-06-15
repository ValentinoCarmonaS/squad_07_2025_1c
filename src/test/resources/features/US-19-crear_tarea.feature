# @crear-tarea
Feature: Crear tarea básica
# Como Gerente de Proyectos
# Quiero crear una tarea dentro de un proyecto
# Para que pueda organizar el trabajo en unidades manejables

  Background: Configuración inicial para todos los escenarios
    Given existe un proyecto con nombre "Project 1", id "1" y con 0 horas estimadas

  @crear-tarea
  Scenario Outline: Crear una nueva tarea con información mínima
    Given se tiene la siguiente información de una tarea:
      | name      | estimatedHours | 
      | <name>    | <hours>        | 
    And la tarea es para el proyecto con id "1"
    When el usuario intenta crear una nueva tarea

    Then la tarea se crea correctamente con los siguientes datos:
      | name      | estimatedHours | status | projectId |
      | <name>    | <hours>        | TO_DO  | 1         |
    And el proyecto tiene una tarea
    And el proyecto contiene la tarea "<name>"
    Examples:
      | name            | hours |
      | Tarea simple 1  | 23    |
      | Tarea diseño    | 15    |
      | Tarea testing   | 8     | 
      | Tarea docs      | 12    | 
      | Tarea deploy    | 5     |
  
  Scenario: Crear tareas para un proyecto impacta en sus horas estimadas
    Given se tiene la siguiente información de tareas:
    | name | estimatedHours | assignedResourceId |
    | Tarea simple 1  | 23          | e1a8216f-7c66-4888-9668-f7c4145cce7e    |
    | Tarea simple 2  | 19          | e1a8216f-7c66-4888-9668-f7c4145cce7e    |
    And las tareas son para el proyecto con id "1"

    When el usuario intenta crear las nuevas tareas

    Then las tareas se crean correctamente con los siguientes datos:
      | name | estimatedHours | assignedResourceId | projectId | status |
      | Tarea simple 1  | 23          | e1a8216f-7c66-4888-9668-f7c4145cce7e    | 1 | TO_DO |
      | Tarea simple 2  | 19          | e1a8216f-7c66-4888-9668-f7c4145cce7e    | 1 | TO_DO |
    And el proyecto contiene las tareas "Tarea simple 1" y "Tarea simple 2"
    And el proyecto tiene 42 horas estimadas

  Scenario: Crear una tarea con información incompleta
    Given se tiene la siguiente información incompleta de una tarea:
      | name |  projectId |
      | Tarea simple 1  | 1 |

    When el usuario intenta crear una nueva tarea

    Then se rechaza la creación de la tarea por información incompleta

  Scenario: Crear una tarea con horas negativas
    Given se tiene la siguiente información de una tarea:
      | name | estimatedHours | projectId |
      | Tarea simple 1  | -1           | 1 |

    When el usuario intenta crear una nueva tarea

    Then se rechaza la creación de la tarea por horas negativas
