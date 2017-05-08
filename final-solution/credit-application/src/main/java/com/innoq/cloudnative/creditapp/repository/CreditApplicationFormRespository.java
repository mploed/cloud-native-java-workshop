package com.innoq.cloudnative.creditapp.repository;

import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationFormRespository extends JpaRepository<CreditApplicationForm, Long> {
}
