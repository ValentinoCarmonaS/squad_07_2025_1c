# Dockerfile
# Usa una imagen base con JDK 17 (ajusta la versión según tu proyecto)
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Damos permisos de ejecución al script de Maven Wrapper y construimos el proyecto
RUN chmod +x ./mvnw && \
	./mvnw clean package -DskipTests && \
	cp target/*.jar app.jar

# Expone el puerto que usa la aplicación
EXPOSE ${PORT:-8080}

# Comando de ejecución
CMD ["java", "-jar", "app.jar"]