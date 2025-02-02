package com.wamad.ecomerce.service;

import com.wamad.ecomerce.model.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${email.form}")
    private String from;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private JavaMailSender javaMailSender;

    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        return simpleMailMessage;
    }

    public void sendVerificationEmail(VerificationToken verificationToken){
        SimpleMailMessage simpleMailMessage = makeMailMessage();
        simpleMailMessage.setTo(verificationToken.getUser().getEmail());
        simpleMailMessage.setSubject("Verify Your Email");
        simpleMailMessage.setText("Please follow the link bellow to verify your email. \n" + frontendUrl + "/auth/verify?token=" + verificationToken.getToken());
        try{
            javaMailSender.send(simpleMailMessage);
        }catch (MailException e){
            System.out.println(e);
        }

    }

}
