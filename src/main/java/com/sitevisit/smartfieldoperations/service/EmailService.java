package com.sitevisit.smartfieldoperations.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String message) {
        System.out.println("=================================");
        System.out.println("EMAIL SENT");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("=================================");
    }
}