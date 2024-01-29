Customer Service

To run the application. please run the below:

- Pre-requisites:
  - java 17
  - support gradle 
  - docker should be running in local
  
Steps to run:
-  ./gradle clean build
-  ./gradlew bootBuildImage 
-  docker run -d -p 8080:8080 customer-api --name customerApi

Application will run in localhost:8080

Swagger ui is available in http://localhost:8080/swagger-ui/index.html

Use the collection folder to import the Happy and Failure scenarios to test in postman