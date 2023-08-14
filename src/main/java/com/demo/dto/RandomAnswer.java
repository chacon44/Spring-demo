package com.demo.dto;

import java.util.Random;

public class RandomAnswer {

    public boolean getAnswer() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
