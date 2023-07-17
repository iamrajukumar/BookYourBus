package com.bus.ticketing.service.bookYourBus.dto;

import lombok.Data;

import java.util.List;

@Data
public class CancelRequestDto {

    String bookingId;

    List<Integer> seatIdsList;

}
