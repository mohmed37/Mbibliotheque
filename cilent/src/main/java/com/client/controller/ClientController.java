package com.client.controller;

import com.client.bean.LibrairieBean;
import com.client.proxies.MlibrairieProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
public class ClientController {
    @Autowired
    MlibrairieProxy mlibrairieProxy;

    Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/")
    public String accueil(Model model) {
        log.info("Envoi requête vers microservice-produits");
        List<LibrairieBean> livres = mlibrairieProxy.listDesLivres();

        model.addAttribute("livres", livres);

        return "Accueil";
    }
}
