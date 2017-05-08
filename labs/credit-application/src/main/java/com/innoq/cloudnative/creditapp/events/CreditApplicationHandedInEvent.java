package com.innoq.cloudnative.creditapp.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditApplicationHandedInEvent implements Serializable {
    private CreditApplicationForm creditApplicationForm;

    public CreditApplicationForm getCreditApplicationForm() {
        return creditApplicationForm;
    }

    public void setCreditApplicationForm(CreditApplicationForm creditApplicationForm) {
        this.creditApplicationForm = creditApplicationForm;
    }
}
