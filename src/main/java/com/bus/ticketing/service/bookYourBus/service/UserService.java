package com.bus.ticketing.service.bookYourBus.service;

import com.bus.ticketing.service.bookYourBus.model.User;
import com.bus.ticketing.service.bookYourBus.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String login(String username, String password){
        User user = userRepo.findByUsernameAndPassword(username, password);
        if(Objects.isNull(user)){
            throw new RuntimeException("Invalid User");
        }
        String token = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return "Basic "+token;
    }

    public User signup(User user){
        user = userRepo.save(user);
        return user;
    }

}
