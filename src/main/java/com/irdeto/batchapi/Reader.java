package com.irdeto.batchapi;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader implements ItemReader<String> {

    private final String[] arr = {"Hello Java Batch Web Service"};
    private int count;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Inside The Read");
        if(count < arr.length){
            return arr[count++];
        }else {
            count = 0;
        }
        return null;
    }
}
