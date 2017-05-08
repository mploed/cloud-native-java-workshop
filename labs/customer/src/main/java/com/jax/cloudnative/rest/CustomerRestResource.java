package com.jax.cloudnative.rest;

import com.jax.cloudnative.domain.Customer;
import com.jax.cloudnative.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "customers")
public class CustomerRestResource {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerRestResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @GetMapping
    public List<Customer> findAllCustomers() {
        System.out.println("retrieving customers");
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping(path = "/{customerId}")
    public void deleteCustomer(@PathVariable String customerId) {
        customerRepository.delete(customerId);

    }

}
