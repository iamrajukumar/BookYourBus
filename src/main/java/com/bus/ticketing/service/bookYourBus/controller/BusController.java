package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.model.Bus;
import com.bus.ticketing.service.bookYourBus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping("")
    public ResponseEntity<?> getBus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate date,
                                    @RequestParam String source,
                                    @RequestParam String destination) {
        return ResponseEntity.ok(busService.getAll(date, source, destination));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(busService.addBus(bus));
    }

    @PutMapping("/update/{busId}")
    public ResponseEntity<?> updateBus(@PathVariable String busId, @RequestBody Bus bus) {
        return ResponseEntity.ok(busService.updateBus(busId, bus));
    }

    @PatchMapping("/delete/{busId}")
    public ResponseEntity<?> deleteBus(@PathVariable String busId) {
        busService.deleteBus(busId);
        return ResponseEntity.ok("Bus deleted successfully with id "+ busId);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate() {
        busService.activateBus();
        return ResponseEntity.ok("Bus activated");
    }

}
