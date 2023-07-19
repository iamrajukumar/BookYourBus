package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.commons.BookingStatus;
import com.bus.ticketing.service.bookYourBus.dto.BookingRequestDto;
import com.bus.ticketing.service.bookYourBus.model.Booking;
import com.bus.ticketing.service.bookYourBus.model.Bus;
import com.bus.ticketing.service.bookYourBus.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BusService busService;

    public Booking addBookings(BookingRequestDto requestDto, int amount){
        Bus bus = busService.getBusById(requestDto.getBusId());
        Booking booking = new Booking();
        booking.setUserId(requestDto.getUserId());
        booking.setBusId(requestDto.getBusId());
        booking.setStartTime(bus.getStartTime());
        booking.setDateOfJourney(requestDto.getJourneyDate());
        booking.setDateOfBooking(LocalDate.now());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setSeatIds(requestDto.getSeatIds());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setAmount(amount);

        return bookingRepo.save(booking);
    }

    public List<Booking> getBookingsOfaUser(String userId, BookingStatus status){
        if(StringUtils.isEmpty(status)){
            return bookingRepo.findByUserId(userId);
        }
        return bookingRepo.findByUserIdAndStatus(userId, status);
    }

    public Booking getBookingsbyId(String id){
        return bookingRepo.findById(String.valueOf(id)).orElse(null);
    }

    public Booking updateBooking(String id, List<Integer> seatIds, int amount){
        Booking booking = getBookingsbyId(id);
        int previousAmount = booking.getAmount();
        booking.setAmount(previousAmount-amount);
        booking.setSeatIds(seatIds);
        booking.setUpdatedAt(LocalDateTime.now());
        return bookingRepo.save(booking);
    }

    public Booking cancelBooking(String id){
        Booking booking = getBookingsbyId(id);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepo.save(booking);
    }

}
