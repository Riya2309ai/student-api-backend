# Student API Backend

A RESTful backend application built with **Java, Spring Boot, Spring Data JPA, and MySQL** for managing student information. The application is containerized using **Docker and Docker Compose** for consistent local development and deployment.

## Tech Stack

* **Java 17**
* **Spring Boot 3.5.14**
* **Spring Data JPA / Hibernate**
* **MySQL 8**
* **Maven**
* **Docker**
* **Docker Compose**
* **Git & GitHub**

## Features

* Create and manage student records through REST APIs
* Persistent data storage using MySQL
* Database interaction using Spring Data JPA
* Maven-based project structure
* Dockerized Spring Boot application
* Docker Compose setup for Spring Boot + MySQL
* MySQL health check to ensure the database is ready before starting the application
* Environment-based database configuration

## Project Structure

```text
studentapi/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/riya/studentapi/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .gitignore
└── README.md
```

## Prerequisites

For running the application with Docker, install:

* [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* Git

You do **not** need to install MySQL locally when using Docker Compose.

## Environment Variables

Database configuration is provided through environment variables.

Create a `.env` file in the project root:

```env
DB_USERNAME=root
DB_PASSWORD=your_password
DB_URL=jdbc:mysql://mysql:3306/student_db
```

> **Important:** Never commit `.env` to GitHub because it contains sensitive credentials. The `.gitignore` file already excludes `.env`.

## Running with Docker

Clone the repository:

```bash
git clone https://github.com/Riya2309ai/student-api-backend.git
cd student-api-backend
```

Create your `.env` file as described above.

Build and start the application:

```bash
docker compose up --build
```

Docker Compose starts:

* **Spring Boot application** → `localhost:8080`
* **MySQL database** → `localhost:3306`

The application container waits for the MySQL health check before starting.

### Run in Background

To start the containers in detached mode:

```bash
docker compose up -d
```

Check running containers:

```bash
docker ps
```

You should see:

```text
student-api
student-api-mysql
```

### Stop the Application

```bash
docker compose down
```

## Running Without Docker

If you want to run the application directly from your development environment:

### Requirements

* Java 17
* Maven
* MySQL 8

Configure the database properties using environment variables and start the application with:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## API

The application runs on:

```text
http://localhost:8080
```

Use Postman, curl, or another API client to interact with the available REST endpoints.

## Docker Architecture

```text
                    Docker Compose
                         │
            ┌────────────┴────────────┐
            │                         │
            ▼                         ▼
     Student API                 MySQL 8
     Spring Boot                student_db
     Port: 8080                 Port: 3306
            │                         │
            └──────────┬──────────────┘
                       │
                  Docker Network
```

The Spring Boot application connects to MySQL using the Docker Compose service name:

```text
mysql:3306
```

rather than `localhost:3306`.

## Useful Docker Commands

View running containers:

```bash
docker ps
```

View application logs:

```bash
docker logs student-api
```

View MySQL logs:

```bash
docker logs student-api-mysql
```

Stop containers:

```bash
docker compose down
```

Rebuild and restart:

```bash
docker compose up --build
```

Stop containers and remove the MySQL volume:

```bash
docker compose down -v
```

> **Warning:** `docker compose down -v` deletes the Docker MySQL volume and therefore removes the database data stored in that volume.

## Development

The project follows a standard Spring Boot layered structure:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

This separation keeps API handling, business logic, data access, and persistence responsibilities organized.

## Author

**Riya Sharma**

* GitHub: [Riya2309ai](https://github.com/Riya2309ai)
* LinkedIn: [Riya Sharma](https://linkedin.com/in/riya-sharma-2a4b0713b/)
