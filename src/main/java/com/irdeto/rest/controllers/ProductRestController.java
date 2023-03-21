package com.irdeto.rest.controllers;

import com.irdeto.rest.entities.Product;
import com.irdeto.rest.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Product Rest Endpoint")
public class ProductRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/products/", method = RequestMethod.GET)
//    @Hidden
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable("product-cache")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @Operation(summary = "Returns a product", description = "Takes the ID and return a single product")
    public @ApiResponse(description = "Product object as response") Product getProduct(@Parameter(description = "Id of the product") @PathVariable("id") int id) {
        logger.info("Find product by id :: {}", id);
        return productRepository.findById(id).get();
    }

    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @RequestMapping(value = "/products/", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @CacheEvict("product-cache")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") int id) {
        productRepository.deleteById(id);
    }
}
