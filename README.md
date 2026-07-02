# 🌐 Spring Boot Blog API

A full-stack blog application built as part of my backend development learning journey.

The project began as a REST API built with Spring Boot and was later expanded with a React frontend, allowing me to learn how frontend and backend applications communicate over HTTP while also exploring AI-assisted software development.

---

# 🎯 Why I built this project

After building a simple Notes API, I wanted to create a more realistic backend application that introduced concepts commonly used in professional Spring Boot development.

My goals were to learn:

- Spring Data JPA
- Database persistence
- DTOs and object mapping
- Request validation
- Global exception handling
- Unit testing with JUnit and Mockito
- How a React frontend consumes a REST API
- How to effectively use an AI coding agent as a learning tool

Rather than asking AI to generate the entire application, I deliberately built the backend myself and used **Claude Code** as a pair-programming assistant for the frontend. I instructed it to work one feature at a time, explain every new React concept it introduced, and encourage me to reason about the code before providing answers.

This approach allowed me to learn React fundamentals while also gaining experience working with an AI coding agent in a realistic development workflow.

---

# 🚀 Features

## Backend

- Create blog posts
- Retrieve all blog posts
- Retrieve a single blog post
- Update existing blog posts
- Delete blog posts

Additional backend features:

- Spring Data JPA persistence
- H2 in-memory database
- Request validation
- Global exception handling
- DTOs for requests and responses
- Manual entity ↔ DTO mapping
- Service layer unit testing

---

## Frontend

A simple React frontend that communicates with the backend API.

Users can:

- View all blog posts
- Create new posts
- Edit existing posts
- Delete posts

The frontend communicates with the backend using HTTP requests to consume the REST API.

---

# 🛠️ Technologies

## Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Jakarta Validation
- Lombok
- JUnit 5
- Mockito
- Maven

## Frontend

- React
- Vite
- Tailwind CSS

---

# 🏗️ Architecture

## Backend

```
Controller
        ↓
Service
        ↓
Repository (JPA)
        ↓
H2 Database
```

The backend follows a layered architecture, separating responsibilities between controllers, services, repositories and DTO mapping.

---

## Frontend

```
React Components
        ↓
HTTP Requests
        ↓
Spring Boot REST API
        ↓
Database
```

The frontend consumes the REST API to perform CRUD operations and display blog posts.

---

# 📚 What I learned

This project introduced several concepts that are commonly used in Spring Boot applications, including:

- Building REST APIs
- Spring Data JPA
- Entity relationships
- DTO design
- Manual object mapping
- Validation annotations
- Exception handling
- Unit testing using JUnit and Mockito

Expanding the project with a frontend also helped me understand:

- React fundamentals
- Component-based UI development
- State management
- Making HTTP requests from a frontend
- Connecting a frontend application to a backend API

---

# 🤖 AI-assisted development

The frontend for this project was developed with assistance from **Claude Code**.

Rather than using AI to generate the application, I used it as a learning tool. I instructed Claude to:

- build one feature at a time
- explain every new React concept
- avoid jumping ahead
- encourage me to reason about the implementation
- answer questions throughout the development process

This allowed me to gain experience using an AI coding agent while ensuring I understood the code it produced.

---

# 🧪 Testing

The backend includes unit tests for the service layer using:

- JUnit 5
- Mockito

The tests focus on verifying business logic independently from the controller layer.

---

# 🌱 Project Evolution

This project evolved over several stages:

### `main`

Backend implementation

- Spring Boot REST API
- JPA persistence
- H2 database
- DTOs
- Validation
- Global exception handling
- Unit testing

### `feature/frontend`

Frontend implementation

- React + Vite
- Tailwind CSS
- CRUD interface
- Connected to the Spring Boot backend
- AI-assisted frontend development using Claude Code

---

# 🔮 Possible Future Improvements

Some ideas I'd like to revisit in the future include:

- PostgreSQL instead of H2
- Dockerising the backend and frontend
- Docker Compose
- GitHub Actions CI pipeline
- Authentication and authorisation
- Pagination
- Search functionality
- Integration testing
- Controller tests
- Deploying the application

---

# ▶️ Running the project

## Backend

```
cd backend
./mvnw spring-boot:run
```

The backend starts on:

```
http://localhost:8080
```

---

## Frontend

```
cd frontend
npm install
npm run dev
```

The frontend starts on:

```
http://localhost:5173
```

---

# 💡 Reflection

This project represents an important milestone in my journey towards building production-ready Spring Boot backend applications.

It was my first project combining:

- database persistence
- REST APIs
- validation
- testing
- frontend integration
- AI-assisted development

It has given me a much better understanding of how a full-stack application is structured and has prepared me for my next goal: building Spring Boot microservices and exploring Docker and CI/CD.
