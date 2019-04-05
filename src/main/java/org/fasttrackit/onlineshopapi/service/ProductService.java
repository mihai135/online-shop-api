package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNorFoundException;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request){
        LOGGER.info("Creating product{}",request);
        Product product = objectMapper.convertValue(request, Product.class);
        return productRepository.save(product);

    }

    public Product getProduct(long id) throws ResourceNorFoundException {
        LOGGER.info("Retreiving product{}",id);
       return  productRepository.findById(id)
               //Optional and lambda expression
               .orElseThrow(()-> new ResourceNorFoundException("Product" + id + " not found")) ;
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNorFoundException {
        LOGGER.info("Updating product{}, {}", id, request);
        Product product = getProduct(id);
        BeanUtils.copyProperties(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id){
        LOGGER.info("Deleting product{}", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product{}", id);
    }
}
