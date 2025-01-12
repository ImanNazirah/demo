
# Spring Boot Project

## Java RESTful API with Spring Boot

This is a demo Spring Boot application that uses the following technologies:

•	MySQL: For database management.

•	Default Cache (ConcurrentHashMap): This application uses ConcurrentHashMap for in-memory caching, which is only suitable for local development or small-scale testing. For production environments, it is recommended to replace the default cache with a more scalable, fault-tolerant caching solution such as Redis or Hazelcast.

•	JWT Token: For securing APIs with authentication and authorization.

### Usage

#### Branch feature/unrestricted-api(No Header Authorization)
In this branch, API calls can be made without including any authentication headers. For example, you can use tools like Postman or cURL without setting an `Authorization` header. This is intended for quick development or testing scenarios where authentication is not needed.

#### Branch master (Header Authorization Required)
In this branch, every API request must include the correct authorization token in the header. For example:

```
Authorization: Bearer <your_token>
```

Without the proper authorization header, the server will respond with an error (e.g., HTTP 401 Unauthorized). This configuration is more secure and represents a scenario where authentication and authorization are required to access the resources.

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

$ ./mvnw clean install -DskipTests

$ ./mvnw spring-boot:run

- Windows

$ mvnw.cmd clean install -DskipTests

$ mvnw.cmd spring-boot:run


Step 3: Test the application

#### Master branch:

- SET TOKEN :

TOKEN=$(curl -s -X POST http://localhost:8080/api/user/login \
-H "Content-Type: application/json" \
-d '{"username": "demo_test1", "password": "mypassword"}' \
-i | grep -i "Authorization:" | sed 's/Authorization: Bearer //')

- GET ALL: 

curl -X GET http://localhost:8080/api/spotify \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json"

- GET BY ID :

curl -X GET http://localhost:8080/api/spotify/<input id> \
-H "Authorization: Bearer $TOKEN" \
-H "Content-Type: application/json"

- POST

curl -X POST http://localhost:8080/api/spotify \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-d '{
"trackName": "<input string>",
"artistName": "<input string>",
"genre": "<input string>",
"popularity": <input number>
}'

- PUT :

curl -X PUT http://localhost:8080/api/spotify/<input id> \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
        "trackName": "<input string>",
        "artistName": "<input string>",
        "genre": "<input string>",
        "popularity": <input number>
      }'

- DELETE

curl -X DELETE http://localhost:8080/api/spotify/<input id>\
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" 

- GET BY QUERY (available query parameter = 'trackName', 'artistName', 'genre','popularity','page', 'pageSize')

curl -X GET "http://localhost:8080/api/spotify/search?genre=<input string>"\
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN"


- GET GLOBAL SEARCH (available query parameter = 'searchText', 'column', 'page', 'pageSize'; where value for column are ['trackName', 'artistName', 'genre','popularity'])

curl -X GET "http://localhost:8080/api/spotify/global-search?searchText=<input string>&column=artistName&column=genre"\
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" 

## References
* https://docs.spring.io/spring-framework/docs/3.0.x/javadoc-api/index.html?org/springframework
* https://docs.oracle.com/javase/8/docs/api/index.html
* https://www.twilio.com/blog/create-rest-apis-java-spring-boot
* https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3
* https://frontbackend.com/spring-boot/what-code-structure-use-for-spring-boot-applications
* https://www.baeldung.com/rest-with-spring-series
* https://www.baeldung.com/java-collections
* https://www.educba.com/what-is-spring-boot/?source=leftnav
* https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
* https://www.bezkoder.com/spring-boot-login-example-mysql/
