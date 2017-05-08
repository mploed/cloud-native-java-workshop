package com.innoq.cloudnative.creditapp.controller;

import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;
import com.innoq.cloudnative.creditapp.domain.Customer;
import com.innoq.cloudnative.creditapp.events.CreditApplicationHandedInEvent;
import com.innoq.cloudnative.creditapp.integration.ICustomerIntegrationService;
import com.innoq.cloudnative.creditapp.repository.CreditApplicationFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class CreditApplicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditApplicationController.class);

    private CreditApplicationFormRepository creditApplicationFormRepository;

    private ICustomerIntegrationService customerService;

    private RedisTemplate redisTemplate;

    @Autowired
    public CreditApplicationController(CreditApplicationFormRepository creditApplicationFormRepository,
                                       ICustomerIntegrationService customerService,
                                       RedisTemplate redisTemplate) {
        this.creditApplicationFormRepository = creditApplicationFormRepository;
        this.customerService = customerService;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("processContainer", new ProcessContainer());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, path = "saveStepOne")
    public String saveStepOne(@ModelAttribute ProcessContainer processContainer, Model model) {
        CreditApplicationForm creditApplicationForm = processContainer.getCreditApplicationForm();
        CreditApplicationForm savedCreditApplicationForm = creditApplicationFormRepository.save(creditApplicationForm);
        processContainer.setCreditApplicationForm(savedCreditApplicationForm);
        model.addAttribute("processContainer", processContainer);
        return "stepTwo";
    }

    @RequestMapping(method = RequestMethod.POST, path = "saveStepTwo")
    public String saveStepTwo(@ModelAttribute ProcessContainer processContainer, Model model) {

        Customer customer = processContainer.getCustomer();
        Customer savedCustomer = customerService.saveCustomerInBackend(customer, processContainer.getCreditApplicationForm().getId());
        processContainer.getCreditApplicationForm().setCustomerId(savedCustomer.getId());
        processContainer.setCustomer(savedCustomer);
        creditApplicationFormRepository.save(processContainer.getCreditApplicationForm());
        LOGGER.info("Remotely and synchronously calling the Customer Application in order to save the customer");
        model.addAttribute("processContainer", processContainer);

        return "applicationSummary";
    }

    @RequestMapping(method = RequestMethod.POST, path = "performScoring")
    public String performScoring(@ModelAttribute ProcessContainer processContainer, Model model) {

        CreditApplicationHandedInEvent event = new CreditApplicationHandedInEvent();
        event.setCreditApplicationForm(processContainer.getCreditApplicationForm());
        redisTemplate.convertAndSend("applied-applications", event);

        LOGGER.info("Remotely and synchronously calling the Scoring Application in order to perform a scoring");

        return "scoringResult";
    }

    @GetMapping(path = "customers")
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }
}