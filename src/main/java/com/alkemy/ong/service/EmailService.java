package com.alkemy.ong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService implements IEmailService{

    @Autowired
    private JavaMailSender jMailSender;

    @Override
    public void sendWelcomeByEmail(String emailAddress, String subject, String body) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("somosfundacionmas@gmail.com");
        emailMessage.setTo(emailAddress);
        emailMessage.setSubject(subject);
        emailMessage.setText(body);

        jMailSender.send(emailMessage);
    }
}

