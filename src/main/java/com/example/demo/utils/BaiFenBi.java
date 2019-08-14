package com.example.demo.utils;

import java.util.Random;

public class BaiFenBi {

    public int getInt(){
        return 1;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(100)+1;
        System.out.println(i);
    }
}
