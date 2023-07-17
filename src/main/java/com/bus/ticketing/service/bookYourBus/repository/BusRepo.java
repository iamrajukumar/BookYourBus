package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusRepo extends MongoRepository<Bus, String> {

    @Query(value = "{ 'source' : ?0, 'destination' : ?1, 'activeTillDate' : {$gte : ?2} }")
    List<Bus> findBySourceAndDestinationAndDate(String source, String destination, LocalDate localDate);

    @Query(value = "{'activeTillDate' : {$gte : ?0} }")
    List<Bus> findActiveBus(LocalDate localDate);

}
