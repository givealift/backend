package com.agh.givealift.service.implementation;

import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final static COD cod = CODFactory.get();

    public JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendMessage(String emailAddress, String subject, String text
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // message.setTo("patrykzygmuntzx@gmail.com");
            message.setTo(emailAddress);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (Exception e) {
            cod.e(e);
        }
    }
} 
    
    
    

