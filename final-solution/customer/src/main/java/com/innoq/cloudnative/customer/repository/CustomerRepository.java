package com.innoq.cloudnative.customer.repository;

import com.innoq.cloudnative.customer.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Long> {
}
