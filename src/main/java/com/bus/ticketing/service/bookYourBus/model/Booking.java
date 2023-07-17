package com.bus.ticketing.service.bookYourBus.model;

import com.bus.ticketing.service.bookYourBus.commons.BookingStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Document("bookings")
public class Booking {

    @Id
    String bookingId;

    String userId;

    String busId;

    LocalDate dateOfJourney;

    LocalTime startTime;

    LocalDate dateOfBooking;

    BookingStatus status;

    List<Integer> seatIds;

}
