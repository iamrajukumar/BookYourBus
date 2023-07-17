package com.bus.ticketing.service.bookYourBus.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
public class User {
    @Id
    String userId;
    String username;
    String userEmail;
    String password;
}
