package com.irdeto.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.irdeto.rest.entities.Product;
import com.irdeto.rest.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RestClientTestUsingMockMVC {

    private static final String PRODUCTS_URL = "/productapi/products/";
    private static final String CONTEXT_PATH = "/productapi";
    private static final Integer PRODUCT_ID = 1;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testGetProducts() throws Exception {
        List<Product> productList = Arrays.asList(buildProduct());
        when(productRepository.findAll()).thenReturn(productList);

        // To check status only
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/productapi/products/")
//                .contextPath("/productapi")
//        ).andExpect(status().isOk());

// To get list result
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/productapi/products/")
//                .contextPath("/productapi")
//        ).andExpect(content()
//                .json("[{'id':1,'name':'MackBook','description':'Its good', 'price':1000}]"));

        // Using object mapper
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(MockMvcRequestBuilders
                .get(PRODUCTS_URL)
                .contextPath(CONTEXT_PATH)
        ).andExpect(content()
                .json(objectWriter.writeValueAsString(productList)));
    }

    @Test
    public void testCreateProduct() throws JsonProcessingException, Exception {
        Product product = buildProduct();
        when(productRepository.save(Mockito.any())).thenReturn(product);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(MockMvcRequestBuilders
                        .post(PRODUCTS_URL)
                        .contextPath(CONTEXT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(product)));
    }

    @Test
    public void testUpdateProduct() throws JsonProcessingException, Exception {
        Product product = buildProduct();
        product.setPrice(1200);
        when(productRepository.save(Mockito.any())).thenReturn(product);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(MockMvcRequestBuilders
                        .put(PRODUCTS_URL)
                        .contextPath(CONTEXT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectWriter.writeValueAsString(product)));
    }

    @Test
    public void testDeleteProduct() throws JsonProcessingException, Exception {
        doNothing().when(productRepository).deleteById(PRODUCT_ID);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(PRODUCTS_URL + PRODUCT_ID)
                        .contextPath(CONTEXT_PATH)
                )
                .andExpect(status().isOk());
    }

    private Product buildProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName("MackBook");
        product.setDescription("Its good");
        product.setPrice(1000);
        return product;
    }
}
