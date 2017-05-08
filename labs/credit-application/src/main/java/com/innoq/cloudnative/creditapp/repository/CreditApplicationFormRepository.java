package com.innoq.cloudnative.creditapp.repository;

import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CreditApplicationFormRepository extends JpaRepository<CreditApplicationForm, Long> {
}
