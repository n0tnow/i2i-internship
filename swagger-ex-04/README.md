# Swagger-ex-04 - Customer Management API

Spring Boot RESTful API with Swagger documentation for managing customer data.

## 🚀 Quick Start

```bash
mvn clean install
mvn spring-boot:run
```

**Access:**
- **API:** http://localhost:8081
- **Swagger UI:** http://localhost:8081/swagger-ui.html

## 📋 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customers` | Get all customers |
| GET | `/api/customers/{id}` | Get customer by ID |
| POST | `/api/customers` | Create customer |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.2.0
- SpringDoc OpenAPI
- Maven

## 📝 Sample Request

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

---
