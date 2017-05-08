package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomerRestClient {
    private RestTemplate restTemplate;


    private DiscoveryClient discoveryClient;
    @Autowired
    public CustomerRestClient(RestTemplate restTemplate,
                              DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @HystrixCommand
    public Customer saveCustomer(Customer customer) {

        //List<ServiceInstance> serviceInstances = discoveryClient.getInstances("CUSTOMER");
        //ServiceInstance customerService = serviceInstances.get(0);
        //System.out.println(customerService.getUri());
        Customer result = restTemplate.postForObject("http://customer/customer", customer, Customer.class);
        return result;
    }


}
