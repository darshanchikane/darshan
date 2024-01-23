package com.LoginSignUp.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
    public String generateOTP() {
        return generateRandomNumber(100000, 999999).toString();
    }

    private Integer generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
