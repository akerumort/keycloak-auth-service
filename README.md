<h1 align="center" id="title">keycloak-auth-service</h1>

<p align="center"><img src="https://socialify.git.ci/akerumort/keycloak-auth-service/image?description=1&font=Jost&language=1&name=1&owner=1&pattern=Floating+Cogs&theme=Light"></p>

This is a Spring Boot application that integrates with Keycloak for managing authentication and authorization. This service demonstrates the functionality of defining access for the corresponding roles. This app also uses the OA2 client and OA2 resource service, as well as their configuration.
Keycloak is an open source product for implementing single sign-on (SSO) with access control capabilities, aimed at modern applications and services. (Wiki)

## ⚙ Features

- User authentication and authorization with Keycloak
- Integration with Spring Security and OAuth2
- Secure access to protected endpoints
- Email is used as the user's login

## 🌐 API Endpoints & Description
- Admin Page

  - GET /auth/admin
  - Summary: Returns the admin page.
  - Description: This endpoint is restricted to users with admin role. It returns an HTML page for admin users.
  - Scratch HTTP Request (in project):
    
    ```bash
    GET http://localhost:8080/auth/admin
    Authorization: Bearer your.jwt.token
    ```

- User Page

  - GET /auth/user
  - Summary: Returns the user page for authenticated users.
  - Description: This endpoint returns an HTML page accessible to any authenticated user.
  - Scratch HTTP Request (in project):
  
    ```bash
    GET http://localhost:8080/auth/user
    Authorization: Bearer your.jwt.token
    ```

**Also scratch to get the JWT token:**

```bash
POST http://localhost:8081/realms/kaservice/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id=kaservice_client&client_secret=yourClientSecretHere&username=your.user@example.com&password=password&grant_type=password
```

## 💻 Used technologies

- **Java** 17
- **Spring Boot**
- **Spring Security**
- **OAuth2 Client**
- **OAuth2 Resource server**
- **Keycloak**: 24.0.0
- **Thymeleaf**
- **Maven**
- **Log4j**
- **SpringDoc OpenAPI**: 2.2.0
- **Docker**

## 🐋 Installation without download & Settings (Keycloak)

1. Build docker container with Keycloak from main directory:
   ```bash
   docker-compose build
   ```
2. Open localhost on the port specified in docker-compose
3. Create new realm
4. Create user (and copy **your** client secret from Credentials)
5. Create client with client authentication (+ set up your root and valid redirect URLs)
6. Create new role and then add this role to user
7. Create mapper for user realm role (Client scopes -> Mappers -> Add mapper by configuration -> User realm role -> Add your name -> click **Multivalued** -> Add Token Claim Name)

## 🛡️ License
This project is licensed under the MIT License. See the LICENSE file for more details.

## ✉️ Contact
For any questions or inquiries, please contact akerumort404@gmail.com.
