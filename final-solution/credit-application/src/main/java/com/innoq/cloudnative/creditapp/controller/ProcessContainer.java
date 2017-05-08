package com.innoq.cloudnative.creditapp.controller;


import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;
import com.innoq.cloudnative.creditapp.domain.Customer;

public class ProcessContainer {
    private Customer customer;
    private CreditApplicationForm creditApplicationForm;

    public ProcessContainer() {
        this.customer = new Customer();
        this.creditApplicationForm = new CreditApplicationForm();
    }

    public CreditApplicationForm getCreditApplicationForm() {
        return creditApplicationForm;
    }

    public void setCreditApplicationForm(CreditApplicationForm creditApplicationForm) {
        this.creditApplicationForm = creditApplicationForm;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
