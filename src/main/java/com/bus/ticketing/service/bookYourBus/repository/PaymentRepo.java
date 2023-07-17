package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.model.Bus;
import com.bus.ticketing.service.bookYourBus.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PaymentRepo extends MongoRepository<Payment, String>  {

    @Query(value = "{'requestId' : ?0 }")
    Payment findPaymentsByRequestId(String requestId);

}
