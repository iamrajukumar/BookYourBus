package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.commons.AppConstants;
import com.bus.ticketing.service.bookYourBus.dto.Seat;
import com.bus.ticketing.service.bookYourBus.dto.UpdateBusRquestDto;
import com.bus.ticketing.service.bookYourBus.model.Bus;
import com.bus.ticketing.service.bookYourBus.model.SeatsInventory;
import com.bus.ticketing.service.bookYourBus.repository.BusRepo;
import com.bus.ticketing.service.bookYourBus.repository.SeatsInventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BusService {

    int thresholdActivationDay = 5;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private SeatsInventoryRepo seatsInventoryRepo;

    public Bus addBus(Bus bus){
        return busRepo.save(bus);
    }

    public void deleteBus(String busId){
        busRepo.deleteById(busId);
    }

    public String updateBus(UpdateBusRquestDto updateBusRquestDto){
        Optional<Bus> busModel = busRepo.findById(updateBusRquestDto.getBusId());
        if(busModel==null){
            throw new RuntimeException("Invalid bus id");
        }
        LocalDate minimumUpdatableDate = LocalDate.now().plusDays(AppConstants.MaxBookingAllowedDays);
        if(updateBusRquestDto.getActiveTillDate().isAfter(minimumUpdatableDate)){
            Bus bus = busModel.get();
            bus.setActiveTillDate(updateBusRquestDto.getActiveTillDate());
            busRepo.save(bus);
        }else{
            throw new RuntimeException("activeTillDate is not valid");
        }
        return "Bus Updated Successfully";
    }

    public List<Bus> getAll(LocalDate date, String source, String destination){
        LocalDate maxAllowedDate = LocalDate.now().plusDays(AppConstants.MaxBookingAllowedDays);
        if(LocalDate.now().isAfter(date) ||   date.isAfter(maxAllowedDate)){
            throw new RuntimeException("Search date is not allowed, please search with another date");
        }
        List<Bus> busList = busRepo.findBySourceAndDestinationAndDate(source, destination, date);
        return busList;
    }

    public void activateBus() {
        List<Bus> busList = busRepo.findActiveBus(LocalDate.now());
        LocalDate date = LocalDate.now();
        for (Bus bus : busList) {
            for (int i = 0; i <= thresholdActivationDay; i++) {
                List<Seat> seatList = new ArrayList<>();
                for (int j = 0; j < bus.getNoOfSeats(); j++)
                    seatList.add(new Seat(j + 1, true));
                LocalDate currDate = date.plusDays(i);
                SeatsInventory seatsInventory = new SeatsInventory(bus.getBusId(), currDate, seatList);
                SeatsInventory existingInventory = seatsInventoryRepo.findByBusIdAndDate(bus.getBusId(), currDate);
                if (Objects.isNull(existingInventory) &&  bus.getActiveTillDate().compareTo(currDate)>=0)
                    seatsInventoryRepo.save(seatsInventory);
            }
        }
    }

    public Bus getBusById(String busId){
        return busRepo.findById(busId).orElse(null);
    }

}
