package com.irdeto.activemq;

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
public class ActiveMqTest {

    @Autowired
    private MessageSender messageSender;

    @Test
    public void testSendAndReceive(){
        messageSender.send("Hello Spring JMS!!");
    }
  }
