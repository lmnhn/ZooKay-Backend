package com.thezookaycompany.zookayproject.utils;

import java.util.Random;

public class RandomTokenGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String Number ="0123456789";

    public static String generateRandomString(int LENGTH) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    // generate otp 6 số
    public static String generateRandomOTP (){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(Number.length());
            char randomNumber = Number.charAt(randomIndex);
            sb.append(randomNumber);
        }

        return sb.toString();
    }


}

