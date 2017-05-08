package com.jax.cloudnative.event;

import com.jax.cloudnative.event.payload.CreditApplicationHandedInEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreditApplicationEventReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditApplicationEventReceiver.class);


    public void receiveMessage(CreditApplicationHandedInEvent event) {
        LOGGER.info("Received a CreditApplicationHandedInEvent for further processing: ");
        LOGGER.info(event.getCreditApplicationForm().toString());
    }
}
