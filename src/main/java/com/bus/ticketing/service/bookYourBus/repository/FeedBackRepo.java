package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.model.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedBackRepo extends MongoRepository<FeedBack, Integer> {
}
