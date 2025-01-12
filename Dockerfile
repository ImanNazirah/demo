FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

#in order to build docker image run below command
# docker build -t demo .
# docker run -d -p 8080:8080 demo