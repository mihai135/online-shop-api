package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.exception.ResourceNorFoundException;
import org.fasttrackit.onlineshopapi.persistence.CustomerRepository;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCutomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.UpdateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(CreateCutomerRequest request){
        LOGGER.info("Creating product{}",request);
        Customer customer = objectMapper.convertValue(request, Customer.class);
        return customerRepository.save(customer);

    }

    public Customer getCustomer(long id) throws ResourceNorFoundException {
        LOGGER.info("Retreiving product{}",id);
        return  customerRepository.findById(id)
                //Optional and lambda expression
                .orElseThrow(()-> new ResourceNorFoundException("Product" + id + " not found")) ;
    }

    public Customer updateCustomer(long id, UpdateCustomerRequest request) throws ResourceNorFoundException {
        LOGGER.info("Updating product{}, {}", id, request);
        Customer customer = getCustomer(id);
        BeanUtils.copyProperties(request, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id){
        LOGGER.info("Deleting product{}", id);
        customerRepository.deleteById(id);
        LOGGER.info("Deleted product{}", id);
    }

}
