package com.example.customer.service;

import com.example.customer.model.CustomerDTO;
import com.example.customer.exception.CustomerNotFoundException;
import java.util.List;

public interface CustomerService {
    
    /**
     * Create a new customer
     * @param customer the customer data
     * @return the created customer with ID
     */
    CustomerDTO createCustomer(CustomerDTO customer);
    
    /**
     * Get a customer by ID
     * @param id the customer ID
     * @return the customer
     * @throws CustomerNotFoundException if customer not found
     */
    CustomerDTO getCustomerById(Long id);
    
    /**
     * Get all customers
     * @return list of all customers
     */
    List<CustomerDTO> getAllCustomers();
    
    /**
     * Update an existing customer
     * @param id the customer ID
     * @param customer the updated customer data
     * @return the updated customer
     * @throws CustomerNotFoundException if customer not found
     */
    CustomerDTO updateCustomer(Long id, CustomerDTO customer);
    
    /**
     * Delete a customer by ID
     * @param id the customer ID
     * @throws CustomerNotFoundException if customer not found
     */
    void deleteCustomer(Long id);
    
    /**
     * Check if a customer exists
     * @param id the customer ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);
    
    /**
     * Get total number of customers
     * @return total count
     */
    long getCustomerCount();
}