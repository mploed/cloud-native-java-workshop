package com.innoq.cloudnative.creditapp.repository;

import com.innoq.cloudnative.creditapp.domain.CustomersForLaterSaving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersForLaterSavingRepository extends JpaRepository<CustomersForLaterSaving, Long> {
}
