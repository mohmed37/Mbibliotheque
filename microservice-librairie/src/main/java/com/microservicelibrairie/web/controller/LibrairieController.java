package com.microservicelibrairie.web.controller;

import com.microservicelibrairie.config.ApplicationLibrairieConfig;
import com.microservicelibrairie.dao.LibrairieRepository;
import com.microservicelibrairie.entities.Librairie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LibrairieController {
    @Autowired
    LibrairieRepository librairieRepository;
    @Autowired
    ApplicationLibrairieConfig appConfigs;

    @GetMapping(value = "/librairies")
    public List<Librairie> listDesLivres() {
        List<Librairie> livres = librairieRepository.findAll();
        List<Librairie> listeLimitee = livres.subList(0, appConfigs.getLimitlivre());

        return listeLimitee;
    }


    @GetMapping(value = "librairies/{id}")
    public Optional<Librairie> recupererUnLivre(@PathVariable("id") long id) {
        Optional<Librairie> livre = librairieRepository.findById(id);
        return livre;
    }


}
