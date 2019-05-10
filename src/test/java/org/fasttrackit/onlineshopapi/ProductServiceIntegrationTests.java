package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNorFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId(){
        Product product = productSteps.createProduct();

        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));

    }
    

    @Test(expected = ResourceNorFoundException.class)
    public void testGetProduct_whenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNorFoundException {
        productService.getProduct(0);
    }

    @Test
    public void testGetProduct_whenExistingId_thenReturnMatchingProduct() throws ResourceNorFoundException {
        Product product = productSteps.createProduct();

        Product retrievedProduct = productService.getProduct(product.getId());

        assertThat(retrievedProduct.getId(), is(product.getId()));
        assertThat(retrievedProduct.getName(), is(product.getName()));

    }

    @Test
    public void testUpdateProduct_whenValidRequestWithAllFields_thenReturnUpdatedProduct() throws ResourceNorFoundException {
        Product createdProduct = productSteps.createProduct();
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(createdProduct.getName() + "edited");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 10);
        request.setSku(createdProduct.getSku() + "edited");

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not (is(createdProduct.getName())));

        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));


    }

    //todo: Implement negative tests for update and tests for update with some fields only

    @Test(expected = ResourceNorFoundException.class)
    public void testDeleteProduct_whenExsistingId_thenProductIsDeleted() throws ResourceNorFoundException {
        Product createdProduct = productSteps.createProduct();
        productService.deleteProduct(createdProduct.getId());

        productService.getProduct(createdProduct.getId());
    }

    @Test
    public void testGetProducts_whenAllCriteriaProvided_thenReturnFilteredResults(){
        Product createdProduct = productSteps.createProduct();

        GetProductsRequest request = new GetProductsRequest();
        request.setPartialName("top");
        request.setMinimumPrice(9.9);
        request.setMaximumPrice(10.1);
        request.setMinimumQuantity(1);

        Page<Product> products = productService.getProducts(request, PageRequest.of(0, 10));

        assertThat(products.getTotalElements(), greaterThanOrEqualTo(1L));

        //todo: for each product from the response assert that all criteria are matched
    }
}
