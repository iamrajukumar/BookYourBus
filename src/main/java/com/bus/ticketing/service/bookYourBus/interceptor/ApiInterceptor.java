package com.bus.ticketing.service.bookYourBus.interceptor;

import com.bus.ticketing.service.bookYourBus.model.User;
import com.bus.ticketing.service.bookYourBus.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class ApiInterceptor implements HandlerInterceptor  {

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");
        String username = getUsernameFromToken(token);
        String password = getPasswordFromToken(token);

        User user = userRepo.findByUsernameAndPassword(username, password);
        if (Objects.isNull(user)) {
            throw new RuntimeException("Invalid User");
        }
        return true;
    }

    public static String getUsernameFromToken(String token) {
        String pair = new String(Base64.decodeBase64(token.substring(6)));
        String userName = pair.split(":")[0];
        return userName;
    }

    public static String getPasswordFromToken(String token) {
        String pair = new String(Base64.decodeBase64(token.substring(6)));
        String password = pair.split(":")[1];
        return password;
    }

}
