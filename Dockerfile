# --- Fase de Compilación ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (para aprovechar la caché de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y generamos el .war
COPY src ./src
RUN mvn clean package -DskipTests

# --- Fase de Ejecución ---
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

# Borramos las apps por defecto de Tomcat para limpiar
RUN rm -rf webapps/*

# Copiamos el .war generado en la fase anterior
COPY --from=build /app/target/f1-manager-web.war webapps/ROOT.war

# Exponemos el puerto 8080
EXPOSE 8080

# Comando para arrancar Tomcat
CMD ["catalina.sh", "run"]
