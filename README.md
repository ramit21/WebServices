# WebServices

Spring MVC based Tomcat application to demonstrate following features:

1. Spring MVC XML based Restful APIs
2. Swagger to document APIs (https://swagger.io/swagger-ui/)
3. Spring scheduler
4. JPA Entity Manager
5. JPA Criteria queries
6. Pagination
7. Load multiple property files
8. Generic exception handlers for controllers
9. Set and Reset cookies from backend
10.Test cases capable to make rest calls using RestTemplate.exchange


Steps to setup the project in IDE
1. Make this project a Tomcat project in IDE with context /WebServices.
2. All dependencies are present in webContent/WEB-INF/lib folder. Add these to the build path.
3. Setup database on local system (create table script in src/resources) and update database configurations in mvc-dispatcher-servelet.xml
4. Start the tomcat server and run the integration test case

