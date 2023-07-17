package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.dto.Seat;
import com.bus.ticketing.service.bookYourBus.model.SeatsInventory;
import com.bus.ticketing.service.bookYourBus.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeatsInventoryRepo extends MongoRepository<SeatsInventory, String> {

    @Query(value = "{'busId' : ?0, 'date' : ?1 }")
    SeatsInventory findByBusIdAndDate(String busId, LocalDate localDate);

    @Query(value = "{'busId' : ?0, 'date' : ?1 }")
    @Update("{ '$set' : { 'seatList' : ?2 } }")
    int updateSeats(String busId, LocalDate date, List<Seat> seatList);

}
