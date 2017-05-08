package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import com.innoq.cloudnative.creditapp.domain.CustomersForLaterSaving;
import com.innoq.cloudnative.creditapp.repository.CustomersForLaterSavingRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerIntegrationService implements ICustomerIntegrationService {
    private RestTemplate restTemplate;

    private CustomersForLaterSavingRepository customersForLaterSavingRepository;

    @Autowired
    public CustomerIntegrationService(RestTemplate restTemplate, CustomersForLaterSavingRepository customersForLaterSavingRepository) {
        this.restTemplate = restTemplate;
        this.customersForLaterSavingRepository = customersForLaterSavingRepository;
    }

    @Override
    @HystrixCommand(fallbackMethod = "putCustomerToTheSide")
    public Customer saveCustomerInBackend(Customer customer, Long creditApplicationId) {
        return restTemplate.postForObject("http://localhost:9091/customers", customer, Customer.class);
    }

    private Customer putCustomerToTheSide(Customer customer, Long creditApplicationId) {
        CustomersForLaterSaving forLaterSaving = new CustomersForLaterSaving();
        forLaterSaving.setCity(customer.getCity());
        forLaterSaving.setFirstName(customer.getFirstName());
        forLaterSaving.setLastName(customer.getLastName());
        forLaterSaving.setPostCode(customer.getPostCode());
        forLaterSaving.setStreet(customer.getStreet());
        forLaterSaving.setCreditApplicationId(creditApplicationId);

        customersForLaterSavingRepository.save(forLaterSaving);

        return customer;
    }
}
