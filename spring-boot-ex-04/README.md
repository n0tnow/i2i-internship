# 📚 Spring Boot Book Management System
## ✨ Features

- **📖 Book Management**: Complete CRUD operations for books
- **🎨 Modern UI**: Responsive Bootstrap interface
- **🔍 View Details**: Detailed book information pages
- **✏️ Edit & Delete**: Easy book management
- **🚀 REST API**: Full RESTful API for developers
- **💾 H2 Database**: In-memory database for development
- **🔐 Spring Security**: Basic security configuration

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.3, Spring Data JPA, Spring Security
- **Frontend**: Thymeleaf, Bootstrap 5.3, Bootstrap Icons
- **Database**: H2 (in-memory)
- **Build Tool**: Maven
- **Java Version**: 21

## 🚀 Quick Start

### Installation & Running

1. **Clone the repository**
```bash
git clone <repository-url>
cd spring-boot-ex-04
```

2. **Run the application**
```bash
mvn spring-boot:run
```

3. **Access the application**
- **Web Interface**: http://localhost:8081
- **API Base URL**: http://localhost:8081/api

## 📱 Usage

### Web Interface
- **Home Page**: http://localhost:8081
- **Book List**: http://localhost:8081/books
- **Add Book**: http://localhost:8081/books/new

### REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/books` | Get all books |
| `POST` | `/api/books` | Create a new book |
| `GET` | `/api/books/{id}` | Get book by ID |
| `PUT` | `/api/books/{id}` | Update book |
| `DELETE` | `/api/books/{id}` | Delete book |
| `GET` | `/api/books/title/{title}` | Search by title |

### Example API Usage

**Get all books:**
```bash
curl http://localhost:8081/api/books
```

**Add a new book:**
```bash
curl -X POST http://localhost:8081/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"1984","author":"George Orwell"}'
```

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/example/spring_boot_ex_04/
│   │   ├── SpringBootEx04Application.java      # Main application
│   │   ├── Book.java                           # Book entity
│   │   ├── BookRepository.java                 # Data repository
│   │   ├── BookController.java                 # REST API controller
│   │   ├── BookWebController.java              # Web controller
│   │   ├── SimpleController.java               # Home page controller
│   │   ├── SecurityConfig.java                 # Security configuration
│   │   ├── RestExceptionHandler.java           # Error handling
│   │   ├── BookNotFoundException.java          # Custom exceptions
│   │   └── BookIdMismatchException.java
│   └── resources/
│       ├── templates/                          # Thymeleaf templates
│       │   ├── home.html
│       │   ├── error.html
│       │   └── books/
│       │       ├── list.html
│       │       ├── add.html
│       │       ├── view.html
│       │       └── edit.html
│       └── application.properties              # App configuration
└── test/                                       # Unit & integration tests
```

## ⚙️ Configuration

### Database
- **Type**: H2 (in-memory)
- **URL**: `jdbc:h2:mem:bootapp`
- **Console**: Available at http://localhost:8081/h2-console (if enabled)

### Server
- **Port**: 8081
- **Security**: Permit all (development mode)

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📝 Sample Data

The application starts with an empty database. Add books through:
- Web interface at http://localhost:8081/books/new
- REST API POST requests to `/api/books`
