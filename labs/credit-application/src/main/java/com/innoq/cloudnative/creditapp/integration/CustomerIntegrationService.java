package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerIntegrationService implements ICustomerIntegrationService {
    private RestTemplate restTemplate;

    @Autowired
    public CustomerIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Customer saveCustomerInBackend(Customer customer) {
        return restTemplate.postForObject("http://localhost:9091/customers", customer, Customer.class);
    }
}
