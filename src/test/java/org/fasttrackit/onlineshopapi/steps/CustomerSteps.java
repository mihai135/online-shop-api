package org.fasttrackit.onlineshopapi.steps;


import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCutomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer() {
        CreateCutomerRequest request = new CreateCutomerRequest();
        request.setFirstname("Mike");
        request.setLastname("Brown");
        request.setAddress("Cluj-Napoca");
        request.setEmail("Mike@yahoo.com");

        return customerService.createCustomer(request);
    }
}
