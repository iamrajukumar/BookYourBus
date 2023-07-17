package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.model.Payment;
import com.bus.ticketing.service.bookYourBus.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<?> viewSeats(@RequestBody Payment payment)  {
        return ResponseEntity.ok(paymentService.doPayment(payment));
    }

}
