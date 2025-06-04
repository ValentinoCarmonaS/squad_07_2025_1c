# Makefile para el proyecto PSA API

include .env
export $(shell sed 's/=.*//' .env)

# Variables
PROJECT_NAME := proyecto-api
MAVEN := ./mvnw
DOTENV := .env

.PHONY: help dev clean install test check-env

help:  ## Muestra esta ayuda
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

dev: check-env clean install  ## Inicia la aplicación en modo desarrollo
	@echo "Iniciando la aplicación en modo desarrollo..."
	@$(MAVEN) spring-boot:run

clean:  ## Limpia el proyecto
	@echo "Limpiando el proyecto..."
	@$(MAVEN) clean

install:  ## Instala dependencias y compila
	@echo "Instalando dependencias..."
	@$(MAVEN) install

test:  ## Ejecuta los tests
	@echo "Ejecutando tests..."
	@$(MAVEN) test

check-env:  ## Verifica que el archivo .env exista
	@if [ ! -f $(DOTENV) ]; then \
		echo "Error: El archivo $(DOTENV) no existe. Crea uno basado en .env.example"; \
		exit 1; \
	fi

flyway-clean: check-env  ## Limpia la base de datos (Flyway)
	@echo "Limpiando la base de datos con Flyway..."
	@$(MAVEN) flyway:clean

flyway-repair: check-env
	@echo "Reparando la base de datos con Flyway..."
	@$(MAVEN) flyway:repair -Dflyway.url=jdbc:postgresql://${PGHOST}/${PGDATABASE}?sslmode=require -Dflyway.user=${PGUSER} -Dflyway.password=${PGPASSWORD}

flyway-migrate: check-env
	@echo "Aplicando migraciones con Flyway..."
	@$(MAVEN) flyway:migrate -Dflyway.url=jdbc:postgresql://$(PGHOST)/$(PGDATABASE)?sslmode=require -Dflyway.user=$(PGUSER) -Dflyway.password=$(PGPASSWORD)

flyway-info: check-env
	@echo "Mostrando información de migraciones Flyway..."
	@$(MAVEN) flyway:info -Dflyway.url=jdbc:postgresql://$(PGHOST)/$(PGDATABASE)?sslmode=require -Dflyway.user=$(PGUSER) -Dflyway.password=$(PGPASSWORD)

db-reset: flyway-clean flyway-migrate  ## Resetea la base de datos (limpia y migra)

format:  ## Formatea el código con Spotless (si lo configuras)
	@echo "Pendiente de implementación..."

check-format:  ## Verifica formato del código
	@echo "Pendiente de implementación..."