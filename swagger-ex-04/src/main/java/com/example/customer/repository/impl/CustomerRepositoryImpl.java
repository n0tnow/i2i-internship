package com.example.customer.repository.impl;

import com.example.customer.model.CustomerDTO;
import com.example.customer.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    
    // In-memory storage
    private final ConcurrentHashMap<Long, CustomerDTO> customers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public CustomerRepositoryImpl() {
        // Initialize with sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        save(new CustomerDTO("John Doe", "john.doe@example.com"));
        save(new CustomerDTO("Jane Smith", "jane.smith@example.com"));
        save(new CustomerDTO("Bob Johnson", "bob.johnson@example.com"));
    }
    
    @Override
    public CustomerDTO save(CustomerDTO customer) {
        if (customer.getId() == null) {
            // New customer - generate ID
            customer.setId(idGenerator.getAndIncrement());
        }
        customers.put(customer.getId(), customer);
        return customer;
    }
    
    @Override
    public Optional<CustomerDTO> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }
    
    @Override
    public List<CustomerDTO> findAll() {
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public boolean deleteById(Long id) {
        return customers.remove(id) != null;
    }
    
    @Override
    public boolean existsById(Long id) {
        return customers.containsKey(id);
    }
    
    @Override
    public long count() {
        return customers.size();
    }
}