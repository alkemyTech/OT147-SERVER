package com.alkemy.ong.service;

public interface IEmailService {
    void sendWelcomeByEmail(String email, String body, String subject);
}
