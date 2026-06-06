# 🛠️ Issue Tracker Backend

A Spring Boot-based Issue Tracking System that supports role-based access, ticket management, and secure authentication using JWT.

---

## 🚀 Features

* 🔐 JWT-based Authentication & Authorization
* 👤 Role-based Access Control (ADMIN, SUPPORT_AGENT, USER)
* 🎫 Ticket Management System
* 📌 Ticket Assignment
* 🔄 Ticket Status Updates
* 💬 Comment System (Planned / In Progress)

---

## 🧑‍💻 Tech Stack

* Java 17+
* Spring Boot
* Spring Security
* JWT (JSON Web Tokens)
* Hibernate / JPA
* MySQL (or any relational DB)
* Maven

---

## 📂 Project Structure

```
src/main/java/com/joel/issue_tracker
│
├── config          # Security & JWT configuration
├── controller      # REST Controllers
├── models          # Entity classes
├── repository      # JPA Repositories
├── service         # Business logic layer
└── dto             # Data Transfer Objects
```

---

## 🔐 Authentication Flow

1. User logs in with credentials
2. Server validates and returns JWT token
3. Client sends token in Authorization header
4. Backend validates token using filter
5. Access granted based on roles

---

## 👥 Roles & Permissions

| Role          | Permissions                                |
| ------------- | ------------------------------------------ |
| USER          | Create tickets, view own tickets           |
| SUPPORT_AGENT | View assigned tickets, update status       |
| ADMIN         | Full access (assign tickets, manage users) |

---

## 🎫 Ticket Lifecycle

```
NEW → ASSIGNED → IN_PROGRESS → RESOLVED → CLOSED
```

---

## 📡 API Endpoints

### 🔑 Auth

* `POST /auth/register`
* `POST /auth/login`

### 👤 Users

* `GET /users`

### 🎫 Tickets

* `POST /tickets` → Create ticket
* `GET /tickets` → View tickets
* `PUT /tickets/{id}/assign/{userId}` → Assign ticket
* `PUT /tickets/{id}/status` → Update status

### 💬 Comments (Planned)

* `POST /tickets/{id}/comments`
* `GET /tickets/{id}/comments`

---

## 📄 Sample Ticket Response

```json
{
  "id": 1,
  "title": "Issue 1",
  "description": "This is description",
  "status": "NEW",
  "priority": "MEDIUM",
  "createdBy": "Joel",
  "assignedTo": null,
  "createdAt": "2026-03-20T19:23:34",
  "updatedAt": null
}
```

---

## ⚙️ Setup & Run

### 1. Clone the repo

```
git clone <repo-url>
cd issue-tracker
```

### 2. Configure Database

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/issue_tracker
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Run the application

```
mvn spring-boot:run
```

---

## 🧠 Design Decisions

* `assignedTo` is nullable → tickets may be unassigned initially
* DTOs used to avoid exposing entities directly
* Role-based access enforced via Spring Security
* Stateless authentication using JWT

---

## 🔮 Future Improvements

* Add Comment System
* Add Ticket Filtering & Search
* Pagination & Sorting
* Email Notifications
* Frontend (React)

---

## 📌 Author

Joel Nada

---

## ⭐ Notes

This project is built as a learning-focused backend system demonstrating real-world API design, security, and scalable architecture principles.
