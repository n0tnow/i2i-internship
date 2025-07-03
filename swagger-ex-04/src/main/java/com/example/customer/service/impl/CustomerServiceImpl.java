package com.example.customer.service.impl;

import com.example.customer.exception.CustomerNotFoundException;
import com.example.customer.model.CustomerDTO;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        // Clear ID to ensure new customer creation
        customer.setId(null);
        return customerRepository.save(customer);
    }
    
    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
    
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        // Check if customer exists
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        // Set the ID and save
        customer.setId(id);
        return customerRepository.save(customer);
    }
    
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }
    
    @Override
    public long getCustomerCount() {
        return customerRepository.count();
    }
}