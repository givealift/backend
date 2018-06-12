package com.agh.givealift;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GiveALiftApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("CEST"));//TODO config
        SpringApplication.run(GiveALiftApplication.class, args);
    }
}
