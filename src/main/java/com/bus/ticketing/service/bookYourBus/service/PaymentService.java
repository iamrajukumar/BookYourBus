package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.model.Payment;
import com.bus.ticketing.service.bookYourBus.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    public Payment doPayment(Payment payment){
        return paymentRepo.save(payment);
    }

    public boolean checkPayment(String requestId){
         Payment payment = paymentRepo.findPaymentsByRequestId(requestId);
         if(Objects.isNull(payment))
             return false;
         return true;
    }

}
