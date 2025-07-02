package com.bilalkaya.hazelcastdemo;

import java.io.Serializable;

/**
 * Person class for Hazelcast Exercise 03
 * Follows the exact format specified in exercise requirements
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private String email;
    private String city;
    
    // Private constructor as required by exercise (never used locally - that's intentional)
    @SuppressWarnings("unused")
    private Person() { }
    
    // Public constructor
    public Person(String name, int age, String email, String city) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.city = city;
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + 
               ", email='" + email + "', city='" + city + "'}";
    }
}