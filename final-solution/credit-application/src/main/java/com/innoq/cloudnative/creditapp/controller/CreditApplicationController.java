package com.innoq.cloudnative.creditapp.controller;
import com.innoq.cloudnative.creditapp.integration.CustomerRestClient;
import com.innoq.cloudnative.creditapp.domain.CreditApplicationForm;
import com.innoq.cloudnative.creditapp.domain.Customer;
import com.innoq.cloudnative.creditapp.integration.FeignCustomerClient;
import com.innoq.cloudnative.creditapp.repository.CreditApplicationFormRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class CreditApplicationController {

    private CreditApplicationFormRespository creditApplicationFormRespository;

    private FeignCustomerClient customerRestClient;


    private static final Logger LOGGER = LoggerFactory.getLogger(CreditApplicationController.class);

    @Autowired
    public CreditApplicationController(CreditApplicationFormRespository creditApplicationFormRespository,
                                       FeignCustomerClient customerRestClient) {
        this.creditApplicationFormRespository = creditApplicationFormRespository;
        this.customerRestClient = customerRestClient;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("processContainer", new ProcessContainer());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, path = "saveStepOne")
    public String saveStepOne(@ModelAttribute ProcessContainer processContainer, Model model) {
        CreditApplicationForm savedCreditApplication = creditApplicationFormRespository.saveAndFlush(processContainer.getCreditApplicationForm());
        processContainer.setCreditApplicationForm(savedCreditApplication);
        model.addAttribute("processContainer", processContainer);
        return "stepTwo";
    }

    @RequestMapping(method = RequestMethod.POST, path = "saveStepTwo")
    public String saveStepTwo(@ModelAttribute ProcessContainer processContainer, Model model) {

        LOGGER.info("Remotely and synchronously calling the Customer Application in order to save the customer");
        Customer customer = customerRestClient.saveCustomer(processContainer.getCustomer());
        CreditApplicationForm creditApplicationForm = creditApplicationFormRespository.findOne(processContainer.getCreditApplicationForm().getId());

        processContainer.setCustomer(customer);
        processContainer.setCreditApplicationForm(creditApplicationForm);

        creditApplicationForm.setCustomerId(customer.getId());
        creditApplicationFormRespository.save(creditApplicationForm);
        model.addAttribute("processContainer", processContainer);

        return "applicationSummary";
    }

    @RequestMapping(method = RequestMethod.POST, path = "performScoring")
    public String performScoring(@ModelAttribute ProcessContainer processContainer, Model model) {

        CreditApplicationForm creditApplicationForm = creditApplicationFormRespository.findOne(processContainer.getCreditApplicationForm().getId());

        LOGGER.info("Remotely and synchronously calling the Scoring Application in order to perform a scoring");

        return "scoringResult";
    }
}