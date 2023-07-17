package com.bus.ticketing.service.bookYourBus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequestDto {

    String requestId;

    String userId;

    String busId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    LocalDate journeyDate;

    List<Integer> seatIds;

}
