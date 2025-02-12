# Hogwarts Artifacts Online

## 📌 Project Overview
Hogwarts Artifacts Online is a **Spring Boot** application designed to manage magical artifacts within the wizarding world. The project provides a structured backend for storing, retrieving, and updating artifact information efficiently.

## 🚀 Features
- RESTful API for managing Hogwarts artifacts.
- CRUD operations (Create, Read, Update, Delete) for artifacts, wizards and users.
- Database integration for persistent data storage.
- Exception handling and validation for API endpoints.
- Well-structured, modular, and scalable Spring Boot architecture.
- TDD approach during building the application

## 🛠️ Technologies Used
- **Spring Boot** - Backend framework
- **Spring Data JPA** - ORM for database operations
- **H2** - Database management
- **Spring Web** - Building RESTful APIs
- **Maven** - Dependency management

## 🏗️ Project Setup
### Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Maven
- A database (H2, PostgreSQL, or any preferred choice)

### Installation Steps
1. **Clone the repository**
   ```sh
   git clone https://github.com/muhmedsaeed/hogwarts-artifacts-online.git
   cd hogwarts-artifacts-online
   ```
2. **Configure the database**
    - Modify `application.properties` (for H2 or PostgreSQL as needed).
3. **Build and Run the Application**
   ```sh
   mvn spring-boot:run
   ```
4. **Access the API**
    - API runs on `http://localhost:8080` by default.

## 🔗 API Endpoints

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



## 📬 Contact
For any inquiries, reach out via GitHub: [Mohamed Saeed](https://github.com/muhmedsaeed/)
