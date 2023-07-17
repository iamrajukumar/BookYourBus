package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.dto.BookingRequestDto;
import com.bus.ticketing.service.bookYourBus.dto.CancelRequestDto;
import com.bus.ticketing.service.bookYourBus.dto.Seat;
import com.bus.ticketing.service.bookYourBus.model.Booking;
import com.bus.ticketing.service.bookYourBus.model.SeatsInventory;
import com.bus.ticketing.service.bookYourBus.repository.SeatsInventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class SeatService {

    @Autowired
    private SeatsInventoryRepo seatsInventoryRepo;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    public List<Seat> viewSeats(String busId, LocalDate date){
        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(busId, date);
        if(Objects.isNull(seatsInventory))
            return new ArrayList<>();
        return seatsInventory.getSeatList();
    }

    public String bookSeats(BookingRequestDto bookingRequestDto) throws InterruptedException {
        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(bookingRequestDto.getBusId(), bookingRequestDto.getJourneyDate());
        List<Integer> requestedSeats = bookingRequestDto.getSeatIds();

        List<Seat> updatedSeats = new ArrayList<>();
        for(Seat seat : seatsInventory.getSeatList()){
            if(requestedSeats.contains(seat.getSeatId())){
                if(Boolean.FALSE.equals(seat.isAvailable())){
                    throw new RuntimeException("seat is already booked");
                }
                updatedSeats.add(new Seat(seat.getSeatId(), false));
            }else{
                updatedSeats.add(seat);
            }
        }
        seatsInventoryRepo.updateSeats(bookingRequestDto.getBusId(), bookingRequestDto.getJourneyDate(), updatedSeats);

        //payment
        TimeUnit.SECONDS.sleep(60);
        if(!paymentService.checkPayment(bookingRequestDto.getRequestId())){
            seatsInventoryRepo.updateSeats(bookingRequestDto.getBusId(), bookingRequestDto.getJourneyDate(), seatsInventory.getSeatList());
            return "Payment Failed";
        }


        bookingService.addBookings(bookingRequestDto);
        return "Booking Successfull";
    }

    public String cancelSeats(CancelRequestDto cancelRequestDto){
        List<Integer> cancelRequestIds = cancelRequestDto.getSeatIdsList();
        List<Integer> existingBookingIds = bookingService.getBookingsbyId(cancelRequestDto.getBookingId()).getSeatIds();

        if(!existingBookingIds.containsAll(cancelRequestIds)){
            throw new RuntimeException("Requested Ids belongs to other bookings");
        }

        Booking booking = bookingService.getBookingsbyId(cancelRequestDto.getBookingId());

        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(booking.getBusId(), booking.getDateOfJourney());
        List<Seat> existingSeats = seatsInventory.getSeatList();

        List<Seat> updatedSeats = new ArrayList<>();
        for(Seat seat : existingSeats){
            if(cancelRequestIds.contains(seat.getSeatId())){
                updatedSeats.add(new Seat(seat.getSeatId(), true));
            }else{
                updatedSeats.add(seat);
            }
        }
        seatsInventoryRepo.updateSeats(booking.getBusId(), booking.getDateOfJourney(), updatedSeats);

        existingBookingIds.removeAll(cancelRequestIds);
        if(CollectionUtils.isEmpty(existingBookingIds)){
            bookingService.cancelBooking(cancelRequestDto.getBookingId());
        }else{
            bookingService.updateBooking(cancelRequestDto.getBookingId(), existingBookingIds);
        }
        return "seat cancelled";
    }

}
