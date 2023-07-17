package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.commons.BookingStatus;
import com.bus.ticketing.service.bookYourBus.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBookings(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.getBookingsbyId(id));
    }

    @GetMapping("/bookings/view")
    public ResponseEntity<?> getBookings(@RequestParam String userId, @RequestParam BookingStatus bookingStatus) {
        return ResponseEntity.ok(bookingService.getBookingsOfaUser(userId, bookingStatus));
    }

}
