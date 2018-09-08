# Registration Demo
This is a simple JAVA Restful API using **JSON Web Token (JWT)** with **Spring Security** and **Spring Boot 2** for user 
to register and get user information after registration. The solution is based on the blog entry 
[Implementing JWT Authentication on Spring Boot APIs](https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/).

The persistence layer of our application is backed by an in-memory database called [H2](http://www.h2database.com/).

We design the table to keep user information as figure below:

![Screenshot Application User Table](application_user_table.png?raw=true "Screenshot Application User Table")

## File Structure
```
register-demo/
 ├──src/                                                        * our source files
 │   ├──main
 │   │   ├──java.com.register.demo
 │   │   │   ├──error
 │   │   │   │   └──ErrorResponse.java                          * custom error response.
 │   │   │   ├──security                                        * Security related folder(JWT, filters)
 │   │   │   │   ├──JWTAuthenticationFilter.java                * the filter responsible for authenticating users
 │   │   │   │   ├──JWTAuthorizationFilter.java                 * the filter responsible for user authorization
 │   │   │   │   ├──SecurityConstants.java                      * security constants
 │   │   │   │   └──WebSecurity.java                            * config file for filter, custom user service etc.
 │   │   │   ├──user
 │   │   │   │   ├──ApplicationUser.java                        * entity database
 │   │   │   │   ├──ApplicationUserRepository.java              * repositories for accessing database
 │   │   │   │   ├──ApplicationUserResponse.java                * application user model
 │   │   │   │   ├──UserController.java                         * REST controller to handle User related requests
 │   │   │   │   └──UserDetailsServiceImpl.java                 * custom UserDatilsService implementataion, to check username/password
 │   │   │   └──RegisterApplication.java                        * Application main enterance
 │   │   └──recources
 │   │       ├──application.properties                          * application variables are configured here
 │   │       └──schema.sql                                      * h2 database query(table creation)
 │   └──test                                                    * Junit test folder
 └──pom.xml                                                     * what maven uses to manage it's dependencies
```

# Requirement
Make sure you have Maven and Java 1.7 or greater

# Usage
Just start the application with the Spring Boot maven plugin (`mvn spring-boot:run`). The application is
running at [http://localhost:8080](http://localhost:8080).

```bash
# clone the project
git clone https://github.com/pyodkhla/registration-demo.git


# change directory
cd registration-demo


# install with mvn
mvn install


# start the server
mvn spring-boot:run
```

There are three endpoints for the demo:

```
/users/sign-up - authentication endpoint with unrestricted access
/login - authentication endpoint with username and password
/users - an endpoint that is restricted to authorized users (a valid JWT token must be present in the request header)
```

If everything works as expected, our RESTful Spring Boot API will be up and running. 
To test it, we can use a tool like Postman or curl to issue request to the available endpoints:

```bash
# Issue a GET request to retrieve users with no JWT
# HTTP 403 Forbidden status is expected
curl http://localhost:8080/users


# Registers a new user
curl -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password",
    "mobileNo": "1234567890",
    "salary": 999999
}' http://localhost:8080/users/register


# Logs into the application (JWT is generated)
curl -i -H "Content-Type: application/json" -X POST -d '{
    "username": "admin",
    "password": "password"
}' http://localhost:8080/login


# Issue a new GET request, passing the JWT
# remember to replace xxx.yyy.zzz with the JWT retrieved above
curl -H "Authorization: Bearer xxx.yyy.zzz" http://localhost:8080/users
```


--

Please feel free to send me some feedback or questions!