package com.client.controller;

import com.client.bean.LibrairieBean;
import com.client.bean.UserBean;
import com.client.proxies.MlibrairieProxy;
import com.client.proxies.MuserProxy;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
@Controller
public class ClientController {
    @Autowired
    MlibrairieProxy mlibrairieProxy;
    @Autowired
    MuserProxy muserProxy;
    @Value("${dir.images}")
    private String imageDir;

    Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/")
    public String accueil(Model model,@RequestParam(name = "motClefAuteur",defaultValue ="") String motClefAuteur,
                          @RequestParam(name = "motClefTitre",defaultValue ="") String motClefTitre
                         /* ,@RequestParam(name="page",defaultValue = "0")int page,
                          @RequestParam(name="size",defaultValue = "5")int size*/) {
        log.info("Envoi requête vers microservice-produits");
       List<LibrairieBean> livres = mlibrairieProxy.listDesLivres( motClefAuteur,motClefTitre/*,page,size*/);
       // List<LibrairieBean> livres = mlibrairieProxy.listDesLivres();
        model.addAttribute("Livres", livres);
        model.addAttribute("motClefAuteur",motClefAuteur);
        model.addAttribute("motClefTitre",motClefTitre);


        return "Accueil";
    }


    @RequestMapping("/users")
    public String user(Model model) {

        log.info("Envoi requête vers microservice-utilisateur");
        List<UserBean> users = muserProxy.listUsers();
        model.addAttribute("users", users);

        return "user";
    }
    @RequestMapping("/form")
    public String formLivre(Model model){
        LibrairieBean livre =new LibrairieBean();
        model.addAttribute("livre",livre);
        return "formLivre";
    }
    @RequestMapping("/save")
    public String saveLivre(@Valid @ModelAttribute("livre")LibrairieBean livre,
                            @RequestParam(name = "picture") MultipartFile file) throws IOException {

       /* if(!(file.isEmpty())){
            livre.setPhoto(file.getOriginalFilename());
        }*/
        livre = mlibrairieProxy.saveLivre(livre);
        if(!(file.isEmpty())){
            livre.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imageDir+livre.getId()));
        }
        mlibrairieProxy.saveLivre(livre);
        return "redirect:/";
   }
    @RequestMapping(value = "/getPhoto",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(String id) throws IOException {
        File f=new File(imageDir+id);
        return IOUtils.toByteArray(new FileInputStream(f));

    }
}
