package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.commons.BookingStatus;
import com.bus.ticketing.service.bookYourBus.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends MongoRepository<Booking, String> {

    @Query(value = "{ 'userId' : ?0 }")
    List<Booking> findByUserId(String userId);

    @Query(value = "{ 'userId' : ?0 , 'status' : ?1 }")
    List<Booking> findByUserIdAndStatus(String userId, BookingStatus status);

}
