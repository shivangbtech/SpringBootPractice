package com.irdeto.firstproject;

import com.irdeto.firstproject.services.PaymentService;
import com.irdeto.firstproject.services.PaymentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPracticeApplicationTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    @Test
    public void testDependencyInjection(){
        Assert.assertNotNull(paymentService);
    }

    @Test
    public void testDependencyInjection2(){
        Assert.assertNotNull(paymentServiceImpl.getPaymentDao());
    }
}
