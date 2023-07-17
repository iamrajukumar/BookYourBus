package com.bus.ticketing.service.bookYourBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("payments")
public class Payment {

    @Id
    String paymentId;

    String requestId;

    int amount;

}
