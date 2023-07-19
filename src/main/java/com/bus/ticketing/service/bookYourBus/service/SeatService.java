package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.commons.AppConstants;
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
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatsInventoryRepo seatsInventoryRepo;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    public List<Seat> viewSeats(String busId, boolean isAvailable, LocalDate date){

        LocalDate maxAllowedDate = LocalDate.now().plusDays(AppConstants.MaxBookingAllowedDays);
        if(date.isAfter(maxAllowedDate)){
            throw new RuntimeException("Date is beyond allowed date");
        }

        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(busId, date);
        if(Objects.isNull(seatsInventory)){
            throw new RuntimeException("Given Bus id is not active on the provided date");
        }

        if(Boolean.FALSE.equals(isAvailable))
            return seatsInventory.getSeatList();

        List<Seat> availableSeats = new ArrayList<>();
        for(Seat seat : seatsInventory.getSeatList()){
            if(Boolean.TRUE.equals(seat.isAvailable()))
                availableSeats.add(seat);
        }
        return availableSeats;
    }

    public String bookSeats(BookingRequestDto bookingRequestDto) throws InterruptedException {
        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(bookingRequestDto.getBusId(), bookingRequestDto.getJourneyDate());
        List<Integer> requestedSeats = bookingRequestDto.getSeatIds();

        checkSeatsAvailability(seatsInventory, requestedSeats);

        List<Seat> updatedSeats = new ArrayList<>();
        int amount=0;
        for(Seat seat : seatsInventory.getSeatList()){
            if(requestedSeats.contains(seat.getSeatId())){
                updatedSeats.add(new Seat(seat.getSeatId(), false));
                amount+=seat.getPrice();
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

        bookingService.addBookings(bookingRequestDto, amount);
        return "Booking Successfull";
    }

    private void checkSeatsAvailability(SeatsInventory seatsInventory, List<Integer> requestedSeats){
        Map<Integer, Seat> seatsMap = seatsInventory.getSeatList().stream().collect(Collectors.toMap(Seat::getSeatId, s -> s));
        for(Integer seatId : requestedSeats){
            if(!seatsMap.containsKey(seatId) || Boolean.FALSE.equals(seatsMap.get(seatId).isAvailable())){
                throw new RuntimeException("selected seats are Invalid or not available");
            }
        }
    }

    public String cancelSeats(CancelRequestDto cancelRequestDto){
        List<Integer> cancelRequestIds = cancelRequestDto.getSeatIdsList();
        List<Integer> existingBookingIds = bookingService.getBookingsbyId(cancelRequestDto.getBookingId()).getSeatIds();

        if(!existingBookingIds.containsAll(cancelRequestIds)){
            throw new RuntimeException("Invalid seatIds or Ids belongs to other bookings");
        }

        Booking booking = bookingService.getBookingsbyId(cancelRequestDto.getBookingId());

        SeatsInventory seatsInventory = seatsInventoryRepo.findByBusIdAndDate(booking.getBusId(), booking.getDateOfJourney());

        int amount=0;
        for(Seat seat : seatsInventory.getSeatList()){
            if(cancelRequestIds.contains(seat.getSeatId())){
                seat.setAvailable(true);
                amount+=seat.getPrice();
            }
        }
        seatsInventoryRepo.updateSeats(booking.getBusId(), booking.getDateOfJourney(), seatsInventory.getSeatList());

        existingBookingIds.removeAll(cancelRequestIds);
        if(CollectionUtils.isEmpty(existingBookingIds)){
            bookingService.cancelBooking(cancelRequestDto.getBookingId());
        }else{
            bookingService.updateBooking(cancelRequestDto.getBookingId(), existingBookingIds, amount);
        }
        return "seat cancelled";
    }

}
