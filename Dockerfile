# Primeiro estágio: Construção do pacote da aplicação
FROM maven:3-openjdk-17 AS build-image

# Define o diretório de trabalho
WORKDIR /to-build-app

# Copia os arquivos necessários
COPY pom.xml .
COPY src ./src

# Instalação das dependências utilizando Maven
RUN mvn dependency:go-offline

# Construção do pacote JAR utilizando Maven
RUN mvn package

# Segundo estágio: Construção da imagem final
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o pacote JAR da construção anterior
COPY --from=build-image /to-build-app/target/your-application.jar ./your-application.jar

# Exposição da porta 8080
EXPOSE 8080

# Ponto de entrada para executar a aplicação
ENTRYPOINT ["java", "-jar", "your-application.jar"]