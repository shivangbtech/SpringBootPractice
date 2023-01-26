package com.irdeto.firstproject.services;

import com.irdeto.firstproject.dao.PaymentDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentDao paymentDao;
}
