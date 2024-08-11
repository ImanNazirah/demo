## Java RESTful API with Spring Boot

Develop restful api by using Java Spring Boot. Note that this is only simple Restful Api without Authorization.

## Environment Setup

* https://www.tutorialspoint.com/spring/spring_environment_setup.htm

## Create DB & TABLE

1. Create databse into your MySQL server

$ CREATE DATABASE api; 

2. Then go to file demo.sql , to create table and insert data

## Steps to run application

Step 1: Open `application.properties` and inject your variable.

Step 2: Run application by using Maven wrapper

- UNIX

$ ./mvnw clean install 

$ ./mvnw spring-boot:run

- Windows

$ mvnw.cmd clean install 

$ mvnw.cmd spring-boot:run


Step 3: Test the application

- GET ALL :
curl -X GET http://localhost:8080/api/spotify

- GET BY ID :
curl -X GET http://localhost:8080/api/spotify/<input id>

- POST :
curl -X POST http://localhost:8080/api/spotify \
  -H "Content-Type: application/json" \
  -d '{
        "trackName": "<input string>",
        "artistName": "<input string>",
        "genre": "<input string>",
        "popularity": <input number>
      }'

- PUT :
curl -X PUT http://localhost:8080/api/spotify/<input id> \
  -H "Content-Type: application/json" \
  -d '{
        "trackName": "<input string>",
        "artistName": "<input string>",
        "genre": "<input string>",
        "popularity": <input number>
      }'

- DELETE
curl -X DELETE http://localhost:8080/api/spotify/<input id>

- GET BY QUERY (available query parameter = 'trackName', 'artistName', 'genre','popularity','page', 'pageSize')
curl -X GET "http://localhost:8080/api/spotify/search?genre=<input string>"


- GET GLOBAL SEARCH (available query parameter = 'searchText', 'column', 'page', 'pageSize'; where value for column are ['trackName', 'artistName', 'genre','popularity'])
curl -X GET "http://localhost:8080/api/spotify/global-search?searchText=<input string>&column=artistName&column=genre"


## References
* https://docs.spring.io/spring-framework/docs/3.0.x/javadoc-api/index.html?org/springframework
* https://docs.oracle.com/javase/8/docs/api/index.html
* https://www.twilio.com/blog/create-rest-apis-java-spring-boot
* https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3
* https://frontbackend.com/spring-boot/what-code-structure-use-for-spring-boot-applications
* https://www.baeldung.com/rest-with-spring-series
* https://www.baeldung.com/java-collections
* https://www.educba.com/what-is-spring-boot/?source=leftnav
