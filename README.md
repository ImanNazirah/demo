## Java RESTful API with Spring Boot

Develop restful api by using Java Spring Boot. Note that this is only simple Restful Api without Authorization.

## Environment Setup

* https://www.tutorialspoint.com/spring/spring_environment_setup.htm

## Pre

## Install Extension in Visual Studio Code 
* Lombok Annotations Suppport for VS Code
* Language Support for Java(TM) by Red Hat
* Maven for Java
* Debugger for Java
* Extension Pack for Java
* Test Runner for Java
* Spring Initializr Java Support
* XML
* Project Manager for Java
* Intellicode

## Steps to run application

Step 1: Open `application.properties` and inject your variable.

Step 2: Run application, and test the application

- GET ALL :
http://localhost:<server.port>/api/spotify


- GET BY ID :
http://localhost:<server.port>/api/spotify/{id}


- POST :
http://localhost:<server.port>/api/spotify
Request Body (JSON) :
{
"trackName": "XXXXXXXXXXXX",
"artistName": "XXXXXXXXX",
"genre": "XXXXXXXXXXXXX",
"popularity": INPUT DIGIT
}


- PUT :
http://localhost:<server.port>/api/spotify/{id}
Request Body (JSON) :
{
"trackName": "XXXXXXXXXXXX",
"artistName": "XXXXXXXXX",
"genre": "XXXXXXXXXXXXX",
"popularity": INPUT DIGIT
}


- DELETE :
http://localhost:<server.port>/api/spotify/{id}

- GET BY QUERY
available query parameter = 'trackName', 'artistName', 'genre','popularity','page', 'pageSize'
http://localhost:<server.port>/api/spotify/search?{key=value}

- GET GLOBAL SEARCH
available query parameter = searchText, column

## References
* https://docs.spring.io/spring-framework/docs/3.0.x/javadoc-api/index.html?org/springframework
* https://docs.oracle.com/javase/8/docs/api/index.html
* https://www.twilio.com/blog/create-rest-apis-java-spring-boot
* https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3
* https://frontbackend.com/spring-boot/what-code-structure-use-for-spring-boot-applications
* https://www.baeldung.com/rest-with-spring-series
* https://www.baeldung.com/java-collections
* https://www.educba.com/what-is-spring-boot/?source=leftnav
