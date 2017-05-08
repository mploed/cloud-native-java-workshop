package com.jax.cloudnative.repo;

import com.jax.cloudnative.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>{
}
