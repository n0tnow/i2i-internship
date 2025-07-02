package com.kafka.student;

public class Student {
    // data member (also instance variable)
    private int id;
    // data member (also instance variable)  
    private String name;
    
    // Default constructor (required for JSON deserialization)
    public Student() {}
    
    // Constructor with parameters
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getter for id
    public int getId() {
        return id;
    }
    
    // Setter for id
    public void setId(int id) {
        this.id = id;
    }
    
    // Getter for name
    public String getName() {
        return name;
    }
    
    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
    
    // toString method for easy printing
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}