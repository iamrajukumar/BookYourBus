package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.commons.BookingStatus;
import com.bus.ticketing.service.bookYourBus.model.FeedBack;
import com.bus.ticketing.service.bookYourBus.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.getBookingsbyId(id));
    }

    @GetMapping("/bookings/view")
    public ResponseEntity<?> getBookings(@RequestParam String userId, @RequestParam(required = false) BookingStatus bookingStatus) {
        return ResponseEntity.ok(bookingService.getBookingsOfaUser(userId, bookingStatus));
    }

    @PostMapping("/bookings/feedback")
    public ResponseEntity<?> getBookings(@RequestBody FeedBack feedBack) {
        return ResponseEntity.ok(bookingService.addFeedback(feedBack));
    }

}
