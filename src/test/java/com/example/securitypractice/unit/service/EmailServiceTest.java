package com.example.securitypractice.unit.service;

import com.example.securitypractice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailService emailService;

    @Test
    void sendEmail() {
    }

    @Test
    void SendEmailTest() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);

        Mockito.doNothing().when(javaMailSender).send(expectedMessage);

        emailService.sendEmail(to, subject, text);

        Mockito.verify(javaMailSender).send(expectedMessage);
    }
}