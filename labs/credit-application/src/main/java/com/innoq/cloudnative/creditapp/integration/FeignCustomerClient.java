package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "customer")
public interface FeignCustomerClient {
    @PostMapping(path = "customers")
    Customer saveCustomer(@RequestBody Customer customer);
}
