# Dockerfile
# Usa una imagen base con JDK 17 (ajusta la versión según tu proyecto)
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Instala Maven (si no usas el Maven Wrapper)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copia los archivos de configuración de Maven
COPY pom.xml .
COPY src ./src
COPY src/main/resources/application.properties ./src/main/resources/

# Construye la aplicación
RUN mvn clean package -DskipTests

# Expone el puerto que usa la aplicación
EXPOSE ${PORT:-8080}

# Comando para ejecutar la aplicación
CMD ["mvn", "spring-boot:run"]