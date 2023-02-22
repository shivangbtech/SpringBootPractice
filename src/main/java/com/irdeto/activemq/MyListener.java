package com.irdeto.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @JmsListener(destination = "${spring.jms.myQueue}")
    public void receive(String message){
        System.out.println("Message Received :: " + message);
    }
}
