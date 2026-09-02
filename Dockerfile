# Estágio 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Cache de dependências Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila sem rodar testes na imagem (testes rodam no CI)
COPY src ./src
RUN mvn clean package -DskipTests -B

# Estágio 2: Imagem final enxuta de execução (Alpine JRE)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o artefato compilado
COPY --from=builder /build/target/*.jar app.jar

# Configuração de porta padrão e variáveis
ENV PORT=8080
EXPOSE 8080

# Execução com suporte a flags dinâmicas de JVM
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]