package com.cenfotec.p3.neuralforge_api.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerationUtil {
    public int generateRandomVerificationCode(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
