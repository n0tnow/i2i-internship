package com.example.customer.repository;

import com.example.customer.model.CustomerDTO;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    
    /**
     * Save a customer (create or update)
     * @param customer the customer to save
     * @return the saved customer with ID
     */
    CustomerDTO save(CustomerDTO customer);
    
    /**
     * Find a customer by ID
     * @param id the customer ID
     * @return Optional containing the customer if found
     */
    Optional<CustomerDTO> findById(Long id);
    
    /**
     * Find all customers
     * @return list of all customers
     */
    List<CustomerDTO> findAll();
    
    /**
     * Delete a customer by ID
     * @param id the customer ID
     * @return true if deleted, false if not found
     */
    boolean deleteById(Long id);
    
    /**
     * Check if a customer exists by ID
     * @param id the customer ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);
    
    /**
     * Count total number of customers
     * @return total count
     */
    long count();
}