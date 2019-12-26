package com.microervicebatch.web.controleur;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class EnvoiMail /*implements Job*/ {

    @Autowired
    private JavaMailSender javaMailSender;


    @GetMapping(value = "/mail")
     public void sendEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("mbehillil@yahoo.fr");

        msg.setSubject("Bibliothèque de Tours - Courrier de rappel");
        msg.setText(" Madame, Monsieur, \n  Malgré notre précedent rappel, vous n'avez toujours pas," +
                " à ce jour, restitué les\n" +
                "      documents ci-dessous.\n");

       javaMailSender.send(msg);

    }

 /*   @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Quartz!"+new Date());

  *//*   SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("mbehillil@yahoo.fr");

        msg.setSubject("Bibliothèque de Tours - Courrier de 2ème rappel");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);*//*
    }*/
}
