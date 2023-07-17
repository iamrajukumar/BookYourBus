package com.bus.ticketing.service.bookYourBus.dto;

import lombok.Data;

@Data
public class Seat {

    int seatId;
    int price;
    boolean available;

    public Seat(int seatId, boolean available){
        this.seatId=seatId;
        this.price=100;
        this.available=available;
    }
}
