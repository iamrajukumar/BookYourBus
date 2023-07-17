package com.bus.ticketing.service.bookYourBus.model;

import com.bus.ticketing.service.bookYourBus.dto.Seat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document("seatsInventory")
public class SeatsInventory {

    @Id
    String inventoryId;

    String busId;

    LocalDate date;

    List<Seat> seatList;

    public SeatsInventory (String busId, LocalDate date, List<Seat> seatList){
        this.busId=busId;
        this.date=date;
        this.seatList=seatList;
    }

}
