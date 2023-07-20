package com.bus.ticketing.service.bookYourBus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("feedback")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedBack {

    @Id
    String id;

    String bookingId;

    int rating;

    String comments;

}
