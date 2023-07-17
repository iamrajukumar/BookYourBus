package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.dto.BookingRequestDto;
import com.bus.ticketing.service.bookYourBus.dto.CancelRequestDto;
import com.bus.ticketing.service.bookYourBus.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/view")
    public ResponseEntity<?> viewSeats(@RequestParam String busId,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(seatService.viewSeats(busId, date));
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookSeats(@RequestBody BookingRequestDto requestDto) throws InterruptedException {
        return ResponseEntity.ok(seatService.bookSeats(requestDto));
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelSeats(@RequestBody CancelRequestDto cancelRequestDto) {
        return ResponseEntity.ok(seatService.cancelSeats(cancelRequestDto));
    }

}
