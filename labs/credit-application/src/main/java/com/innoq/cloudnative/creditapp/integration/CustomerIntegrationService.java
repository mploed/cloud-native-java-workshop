package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import com.innoq.cloudnative.creditapp.domain.CustomersForLaterSaving;
import com.innoq.cloudnative.creditapp.repository.CustomersForLaterSavingRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerIntegrationService implements ICustomerIntegrationService {
    private RestTemplate restTemplate;

    private CustomersForLaterSavingRepository customersForLaterSavingRepository;

    private DiscoveryClient discoveryClient;

    @Autowired
    public CustomerIntegrationService(RestTemplate restTemplate, CustomersForLaterSavingRepository customersForLaterSavingRepository, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.customersForLaterSavingRepository = customersForLaterSavingRepository;
        this.discoveryClient = discoveryClient;
    }

    @Override
    @HystrixCommand(fallbackMethod = "putCustomerToTheSide")
    public Customer saveCustomerInBackend(Customer customer, Long creditApplicationId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("customer");
        ServiceInstance customerServiceInstance = serviceInstances.get(0);
        return restTemplate.postForObject(customerServiceInstance.getUri() + "/customers", customer, Customer.class);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallBackOnList")
    public List<Customer> listCustomers() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("customer");
        ServiceInstance customerServiceInstance = serviceInstances.get(0);
        return restTemplate.getForObject(customerServiceInstance.getUri() + "/customers", List.class);
    }

    private List<Customer> fallBackOnList() {
        return new ArrayList<Customer>();
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
