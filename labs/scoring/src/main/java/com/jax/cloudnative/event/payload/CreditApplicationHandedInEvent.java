package com.jax.cloudnative.event.payload;

import java.io.Serializable;

public class CreditApplicationHandedInEvent implements Serializable {
    private CreditApplicationForm creditApplicationForm;

    public CreditApplicationForm getCreditApplicationForm() {
        return creditApplicationForm;
    }

    public void setCreditApplicationForm(CreditApplicationForm creditApplicationForm) {
        this.creditApplicationForm = creditApplicationForm;
    }
}
