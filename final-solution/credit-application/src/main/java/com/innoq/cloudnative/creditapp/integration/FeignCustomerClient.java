package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("customer")
public interface FeignCustomerClient {
    @PostMapping(value = "/customer", consumes = "application/json")
    Customer saveCustomer(Customer customer);
}
