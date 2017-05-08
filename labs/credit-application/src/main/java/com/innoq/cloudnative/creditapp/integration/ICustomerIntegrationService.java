package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;

import java.util.List;

public interface ICustomerIntegrationService {
    Customer saveCustomerInBackend(Customer customer, Long creditApplicationId);
    List<Customer> listCustomers();

}
