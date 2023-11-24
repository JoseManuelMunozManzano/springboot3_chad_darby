# README

## Overview
This example shows the basic process for registering a new user using a REST API.

The application exposes the public endpoint: /register

It expects the following JSON

Endpoint: `/register`

HTTP Method: `POST`

```
{
"userName" : "theUserName",
"password" : "thePassword",
"roleName" : "ROLE_MANAGER"
}
```

This will add a new user to the database with the role of MANAGER.

Successful requests will return a 200 status code (ok).

The app checks if the username already exists, if so then it will return 400 status code (bad request).

## Database Set Up
1. Run the following database scripts
    * sql-scripts/01-employee-directory.sql
    * 02-setup-spring-security-demo-database-hibernate-bcrypt.sql

## Test the App
1. Run the Spring Boot application: `CruddemoApplication.java`
2. Run POSTMAN
3. Send the following request
   * POST http://localhost:8080/register
   * Body: raw -> JSON  
   ```
   {
   "userName" : "scott",
   "password" : "tiger123",
   "roleName" : "ROLE_MANAGER"
   }
   ```
4. This will return status code of 200

You now have a new user in the database with the role of MANAGER. 

Congratulations.