package com.agh.givealift.configuration;

import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    private static final COD cod = CODFactory.get();
    @Bean
    public String notificationKey() {
        String key = System.getenv("notification.key");
        cod.i("NOTIFICATION KEY: " + key);
        return key;
    }

}
