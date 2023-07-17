package com.bus.ticketing.service.bookYourBus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document("bus")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bus {

    @Id
    String busId;

    String vehicleNumber;

    String routeNo;

    int noOfSeats;

    LocalTime startTime;

    LocalTime endTime;

    String duration;

    String distance;

    String source;

    String destination;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    LocalDate activeTillDate;

}