package com.example.customer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Customer data transfer object")
public class CustomerDTO {
    
    @Schema(description = "Unique ID of the customer", example = "1")
    private Long id;
    
    @NotBlank(message = "Name cannot be blank")
    @Schema(description = "Full name of the customer", example = "John Doe")
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;
    
    // Default constructor
    public CustomerDTO() {}
    
    // Constructor with all fields
    public CustomerDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Constructor without id (for creation)
    public CustomerDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // toString method
    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
    // equals and hashCode methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomerDTO that = (CustomerDTO) obj;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}