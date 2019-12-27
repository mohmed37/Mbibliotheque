package com.microervicebatch.web.controleur;

import com.microervicebatch.dao.BatchDao;
import com.microervicebatch.entities.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ListIterator;


@RestController
public class EnvoiMail {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private BatchDao batchDao;


    @Scheduled(cron = "0 10 19 * * * ")
    public void sendEmail() {
        List<Batch> list = batchDao.findAll();
        for (int i =0; i < list.size(); i++){
            long id= list.get(i).getId();

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(batchDao.findById(id).get().getEmailTo());

            msg.setSubject("Bibliothèque de Tours - Courrier de rappel");
            msg.setText(batchDao.findById(id).get().getContent());
            javaMailSender.send(msg);}

    }
    @GetMapping(value = "/mailAll")
    public List<Batch>mailAll(){
        return batchDao.findAll();
    }

    @PostMapping(value = "/saveListBatch")
    public ResponseEntity<Batch> saveListBatch(@RequestBody Batch batch){
       Batch saveBatch= batchDao.save(batch);

        return new ResponseEntity<Batch>(saveBatch, HttpStatus.CREATED);
    }
}
