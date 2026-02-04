# Estágio 1: Build
# Usamos diretamente a imagem do JDK 25, que é garantida
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Copia os arquivos de configuração do Maven Wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Garante que o script tenha permissão de execução e baixa as dependências
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copia o código fonte e gera o jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio 2: Runtime (Leve)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Segurança: usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/pace-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="-XX:+UseParallelGC -XX:MaxRAMPercentage=75.0"

EXPOSE 9091


ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]