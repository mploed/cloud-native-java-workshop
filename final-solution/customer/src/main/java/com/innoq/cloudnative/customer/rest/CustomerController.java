package com.innoq.cloudnative.customer.rest;

import com.innoq.cloudnative.customer.domain.Customer;
import com.innoq.cloudnative.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        System.out.println("saving a new customer");
        return this.customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }
}
