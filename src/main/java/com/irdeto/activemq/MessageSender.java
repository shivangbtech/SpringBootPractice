package com.irdeto.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${spring.jms.myQueue}")
    private String queue;

    public void send(String message){
        System.out.println("Message Sent :: " + message);
        // using convertAndSend
//        jmsTemplate.convertAndSend(queue, message);

        // using MessageCreator
        MessageCreator messageCreator = s -> s.createTextMessage(message);
        jmsTemplate.send(queue, messageCreator);
    }
}
