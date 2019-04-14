package org.fasttrackit.onlineshopapi;


import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCutomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId(){
        Customer customer = createCustomer();

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));

    }

    private Customer createCustomer() {
        CreateCutomerRequest request = new CreateCutomerRequest();
        request.setFirstname("Mike");
        request.setLastname("Brown");
        request.setAddress("Cluj-Napoca");
        request.setEmail("Mike@yahoo.com");

        return customerService.createCustomer(request);
    }
}
