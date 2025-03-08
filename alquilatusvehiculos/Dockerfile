# ETAPA BUILD
# Usamos una imagen base con Java 21
FROM openjdk:21-jdk-slim AS build

# Establecemos el directorio de trabajo en el contenedor
WORKDIR /app

# Instalamos Maven
RUN apt-get update && apt-get install -y maven

# Copiamos el archivo pom.xml y el código fuente
COPY pom.xml .

# Descargamos las dependencias de Maven sin compilar el código
RUN mvn dependency:go-offline

# Copiamos el código fuente del proyecto
COPY src ./src

# Compilamos y empaquetamos la aplicación con Maven (esto crea un archivo JAR ejecutable)
RUN mvn clean package -DskipTests

# ETEPA RUN
# Usamos una imagen base para ejecutar la aplicación Java
FROM openjdk:21-jdk-slim

# Establecemos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR empaquetado desde la etapa anterior
COPY --from=build /app/target/alquilatusvehiculos-0.0.1-SNAPSHOT.jar alquilatusvehiculos.jar

# Exponemos el puerto en el que corre la aplicación (ajusta si es necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "alquilatusvehiculos.jar"]

