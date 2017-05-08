package com.innoq.cloudnative.creditapp.integration;

import com.innoq.cloudnative.creditapp.domain.Customer;

public interface ICustomerIntegrationService {
    Customer saveCustomerInBackend(Customer customer, Long creditApplicationId);
}
