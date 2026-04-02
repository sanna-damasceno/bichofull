# Estágio de Build (Compila o código)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio de Execução (Roda o sistema)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia o arquivo .jar gerado para dentro da imagem
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]