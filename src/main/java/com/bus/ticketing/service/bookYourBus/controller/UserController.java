package com.bus.ticketing.service.bookYourBus.controller;

import com.bus.ticketing.service.bookYourBus.model.User;
import com.bus.ticketing.service.bookYourBus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.signup(user));
    }

}
