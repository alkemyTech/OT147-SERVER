package com.alkemy.ong.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    SendGrid sendGrid;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.sendgrid.from-address}")
    private String fromAddress;

    public Response sendEmail(String toAddress, String subject, Content content) {
        Email from = new Email(fromAddress);
        Email to = new Email(toAddress);

        Mail mail = new Mail(from, subject, to, content);
        mail.setReplyTo(new Email(fromAddress));

        Request request = new Request();
        Response response = null;

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGrid.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public void welcomeMail(String email) {
     //   String subject = "Bienvenid@ a Somos Más";
       // Content content = new Content("text/html", templateEngine.process("plantilla_email.html", context));
      //  this.sendEmail(email, subject, content);
    }
}
