package com.clientiu.controller;


import com.clientiu.bean.LibrairieBean;
import com.clientiu.proxies.MlibrairieProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    MlibrairieProxy mlibrairieProxy;
    @RequestMapping("/")
    public String accueil(Model model){

        List<LibrairieBean>livres=mlibrairieProxy.listDesLivres();

        model.addAttribute("livres",livres);
        return "Accueil";
    }

}