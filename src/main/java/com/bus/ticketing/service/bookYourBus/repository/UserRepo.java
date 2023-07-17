package com.bus.ticketing.service.bookYourBus.repository;

import com.bus.ticketing.service.bookYourBus.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    @Query(value = "{'username' : ?0, 'password' : ?1 }")
    User findByUsernameAndPassword(String username, String password);

}

