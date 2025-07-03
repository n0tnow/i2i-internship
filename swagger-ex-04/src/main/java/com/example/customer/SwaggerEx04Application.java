package com.example.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Customer Management API",
        description = "RESTful API for managing customer data with full CRUD operations",
        version = "1.0.0",
        contact = @Contact(
            name = "Development Team",
            email = "dev@example.com"
        )
    )
)
public class SwaggerEx04Application {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerEx04Application.class, args);
        System.out.println("🚀 Customer Management API is running!");
        System.out.println("📖 Swagger UI: http://localhost:8081/swagger-ui.html");
        System.out.println("📋 API Docs: http://localhost:8081/api-docs");
    }
}