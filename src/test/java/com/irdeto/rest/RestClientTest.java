package com.irdeto.rest;

import com.irdeto.rest.entities.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestClientTest {

    @Value("${productrestapi.services.url}")
    private String baseUrl;

    @Test
    public void testGetProduct() {
//        System.out.println("BaseUrl:: " + baseUrl);
        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(baseUrl + "1", Product.class);
        Assert.assertNotNull(product);
        Assert.assertEquals("irdeto", product.getName());
    }

    @Test
    public void testCreateProduct() {
        RestTemplate restTemplate = new RestTemplate();
        Product product = new Product();
        product.setName("irdeto again");
        product.setDescription("Hello Moto");
        product.setPrice(5000);
        Product productResponse = restTemplate.postForObject(baseUrl, product, Product.class);
        Assert.assertNotNull(productResponse);
        Assert.assertNotNull(productResponse.getId());
        Assert.assertEquals("irdeto again", product.getName());
    }

    @Test
    public void testUpdateProduct() {
        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(baseUrl + "1", Product.class);
        product.setPrice(2000);
        restTemplate.put(baseUrl, product);
    }
}
