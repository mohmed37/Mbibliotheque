package com.client.controller;


import com.client.bean.GenreBean;
import com.client.bean.LibrairieBean;
import com.client.bean.LivreReserveBean;
import com.client.bean.UserBean;
import com.client.proxies.MlibrairieProxy;
import com.client.proxies.MuserProxy;
import com.client.service.IUserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    MlibrairieProxy mlibrairieProxy;
    @Autowired
    MuserProxy muserProxy;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    IUserService userService;

    @Value("${dir.images}")
    private String imageDir;

    Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/")
    public String accueil(Model model,@RequestParam(name = "motClefAuteur",defaultValue ="") String motClefAuteur,
                          @RequestParam(name = "motClefTitre",defaultValue ="") String motClefTitre
                          ,@RequestParam(name="page",defaultValue = "0")int page,
                          @RequestParam(name="size",defaultValue = "8")int size) {
        log.info("Envoi requête vers microservice-produits");
        LibraryResponse pageLivres = mlibrairieProxy.listDesLivres( motClefAuteur,motClefTitre,page,size);
        int pagesCount1=pageLivres.getContent().size();
        int[]pages=new int[pagesCount1];
        for (int j=0;j<pagesCount1;j++) pages[j]=j;
        model.addAttribute("pages",pages);
        model.addAttribute("pageLivres", pageLivres.getContent());
        model.addAttribute("pageCourant",page);
        List<GenreBean>genres=mlibrairieProxy.genreLivreAll();
        model.addAttribute("genres",genres);
        return "Accueil";
    }
    @RequestMapping("/selectionParGenre")
    public String selectionParGenre(Model model,@RequestParam(name = "genre",defaultValue =" ") String genre) {
        log.info("Envoi requête vers microservice-produits");
        List<LibrairieBean> pageLivres = mlibrairieProxy.findByGenre(genre);
        model.addAttribute("pageLivres", pageLivres);
        List<GenreBean>genres=mlibrairieProxy.genreLivreAll();
        model.addAttribute("genres",genres);
        return "Accueil";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/detailLivre")
    public String detailLivre(Model model,@RequestParam(name="id",defaultValue = " ")long id){
    LibrairieBean detailLivre = mlibrairieProxy.recupererUnLivre(id);
    model.addAttribute("detailLivre",detailLivre);
        return "detailLivre";
    }



    @RequestMapping("/userLocation")
    public String user(Model model) {
        log.info("Envoi requête vers microservice-utilisateur");

        UserBean userConnec=userService.getUserConnec();
        model.addAttribute("userConnect", userConnec);

      List<LivreReserveBean> livresLocation = mlibrairieProxy.findByLocation(userConnec.getNum());
      model.addAttribute("livresLocation", livresLocation);

       Date dateJour=new Date();
        model.addAttribute("dateJour",dateJour);
        return "user";
    }



    @RequestMapping("/form")
    public String formLivre(Model model){
        LibrairieBean livre =new LibrairieBean();
        model.addAttribute("livre",livre);
        UserBean userConnec=userService.getUserConnec();
        model.addAttribute("userConnect",userConnec);
        List<GenreBean> genres=mlibrairieProxy.genreLivreAll();
        model.addAttribute("genres",genres);
        return "formLivre";
    }

    @RequestMapping("/save")
    public String saveLivre(@Valid @ModelAttribute("livre")LibrairieBean livre,
                            @RequestParam(name = "picture") MultipartFile file,@RequestParam("iDgenre") int iDgenre) throws IOException {

       /* if(!(file.isEmpty())){
            livre.setPhoto(file.getOriginalFilename());
        }*/

       livre.setGenre(mlibrairieProxy.GenreLivre(iDgenre).get());

        livre = mlibrairieProxy.saveLivre(livre);
        if(!(file.isEmpty())){
            livre.setPhoto(file.getOriginalFilename());
            file.transferTo(new File(imageDir+livre.getId()));
        }
        mlibrairieProxy.saveLivre(livre);
        return "redirect:/form";
   }
    @RequestMapping(value = "/getPhoto",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(String id) throws IOException {
        File f=new File(imageDir+id);
        return IOUtils.toByteArray(new FileInputStream(f));

    }
    @RequestMapping(value ="/username")
    public UserBean findUserByUsername(@RequestParam(name = "username",defaultValue ="") String username) {
       return muserProxy.findUserByUsername(username);}


    @RequestMapping(value = "/prolongation")
    public String prolongation(long id){
           mlibrairieProxy.prolongation(id);

        return "redirect:/userLocation";
    }



}
