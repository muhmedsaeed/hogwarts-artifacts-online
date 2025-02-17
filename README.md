# Hogwarts Artifacts Online

## 📌 Project Overview
Hogwarts Artifacts Online is a **Spring Boot** application designed to manage magical artifacts within the wizarding world. The project provides a structured backend for storing, retrieving, and updating artifact information efficiently.

## 🚀 Features
- RESTful API for managing Hogwarts artifacts.
- CRUD operations (Create, Read, Update, Delete) for artifacts, wizards and users.
- Database integration for persistent data storage.
- Exception handling and validation for API endpoints.
- TDD approach during building the application
- JWT-based authentication and authorization.
- OAuth2 support for secure authentication.
- Well-structured, modular, and scalable Spring Boot architecture.

## 🛠️ Technologies Used
- **Spring Boot** - Backend framework
- **Spring Security** - Authentication & Authorization
- **JWT** - Token-based authentication
- **OAuth2** - Secure authentication
- **Spring Data JPA** - ORM for database operations
- **H2** - Database management
- **Spring Web** - Building RESTful APIs
- **Maven** - Dependency management


## 🔗 API Endpoints
   **Access the API**
      - API runs on `http://localhost:8080/api/v1` by default.

### 1- Artifacts
| Method | Endpoint            | Description            |
|--------|---------------------|------------------------|
| GET    | `/artifacts`        | Get all artifacts      |
| GET    | `/artifacts/{id}`   | Get artifact by ID     |
| POST   | `/artifacts/add`    | Add a new artifact     |
| PUT    | `/artifacts/{id}`   | Update an artifact     |
| DELETE | `/artifacts/{id}`   | Delete an artifact     |

### 2- Wizards
| Method | Endpoint                       | Description                    |
|--------|--------------------------------|--------------------------------|
| GET    | `/wizards`                     | Get all wizards                |
| GET    | `/wizards/{id}`                | Get wizard by ID               |
| POST   | `/wizards/add`                 | Add a new wizard               |
| PUT    | `/wizards/{id}`                | Update a wizard                |
| DELETE | `/wizards/{id}`                | Delete a wizard                |
| PUT    | `/wizards/{id}/artifacts/{id}` | Assign an artifact to a wizard |

### 3- Users
| Method | Endpoint            | Description                |
|--------|---------------------|----------------------------|
| GET    | `/users`            | Get all users              |
| GET    | `/users/{id}`       | Get user by ID             |
| POST   | `/users`            | Add a new user             |
| PUT    | `/users/{id}`       | Update an user             |
| DELETE | `/users/{id}`       | Delete an user             |
| POST   | `/users/login`      | For Login Authentication   |


## 🔐 Authentication & Security
- JWT authentication is used for secure API access.
- OAuth2 support for external authentication providers.
- Endpoints requiring authentication use Bearer tokens.

## 📬 Contact
For any inquiries, reach out via GitHub: [Mohamed Saeed](https://github.com/muhmedsaeed/)
