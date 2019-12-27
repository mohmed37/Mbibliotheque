package com.client.controller;

import com.client.bean.BatchBean;
import com.client.bean.LibrairieBean;
import com.client.bean.LivreReserveBean;
import com.client.bean.UserBean;
import com.client.proxies.MbatchProxy;
import com.client.proxies.MlibrairieProxy;
import com.client.proxies.MuserProxy;
import com.client.service.IUserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    MlibrairieProxy mlibrairieProxy;

    @Autowired
    MuserProxy muserProxy;
    @Autowired
    MbatchProxy mbatchProxy;
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
                          @RequestParam(name="size",defaultValue = "3")int size) {
        log.info("Envoi requête vers microservice-produits");
        List<LibrairieBean> pageLivres = mlibrairieProxy.listDesLivres( motClefAuteur,motClefTitre,page,size);
      /*  int pagesCount=pageLivres.getTotalPages();
        int[]pages=new int[pagesCount];
        for (int i=0;i<pagesCount;i++) pages[i]=i;
        model.addAttribute("pages",pages);*/
        model.addAttribute("pageLivres", pageLivres);
        return "Accueil";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
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
    @RequestMapping(value ="/username")
    public UserBean findUserByUsername(@RequestParam(name = "username",defaultValue ="") String username) {
       return muserProxy.findUserByUsername(username);}


    @RequestMapping(value = "/prolongation")
    public String prolongation(long id){
           mlibrairieProxy.prolongation(id);

        return "redirect:/userLocation";
    }
    @RequestMapping(value = "/mail")
       public String envoiMail(BatchBean batchBean){
        SimpleDateFormat formater = null;
        Date dateJour= new Date();
        formater = new SimpleDateFormat("'le' dd/MM/yyyy");
        List <LivreReserveBean>listLivresReserves=mlibrairieProxy.livreReservesAll();
        for (int i=0;i<listLivresReserves.size();i++){
           LivreReserveBean livreReserveBean =listLivresReserves.get(i);
            if (livreReserveBean.getRendreLivre()==true ){
                        Optional<UserBean> client = muserProxy.findById(livreReserveBean.getIdClient());
                        if (livreReserveBean.getMailEnvoye()==false){
                        batchBean.setContent("Bibliothèque Municipale de Tours\n" +
                                "    2bis AV. ANDRE MALRAUX\n" +
                                "    37042 TOURS CEDEX\n" +
                                "    02 47 05 47 33\n" +
                                "    secretariat@bm-tours.fr\n" +
                                "\n" +
                                "\n" +
                                "                                                  M. "+" "+ client.get().getNom()+" "
                                + client.get().getPrenom()+"\n" +
                                "                                                  \n" +
                                "                                                  10 RESIDENCE DU GRAND CEDRE\n" +
                                "                                                  \n" +
                                "                                                  37550 ST AVERTIN\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "                                                  Tours," +formater.format(dateJour)+"\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "    Madame, Monsieur, \n" +
                                "\n" +
                                "    Malgré notre précedent rappel, vous n'avez toujours pas, à ce jour, restitué les\n" +
                                "      documents ci-dessous.\n" +
                                "    Merci de les rendre sans tarder, afin que d'autres lecteurs puissent en profiter.\n" +
                                "    Tant que vous ne les aurez pas restitués, il ne vous sera pas possible d'emprunter\n" +
                                "      d'autres documents, et ce dans n'importe quelle bibliothèque du réseau.\n" +
                                "\n" +
                                "    Si vous ne retrouvez plus les documents qui vous sont demandés, vous avez la\n" +
                                "      possibilité de les remplacer par un exemplaire identique.\n" +
                                "\n" +
                                "    Si ce courrier vous parvient après le retour des documents réclamés, merci de ne\n" +
                                "      pas en tenir compte. Pensez, pour vos prochains emprunts, à ne pas dépasser les\n" +
                                "      durées de prêt.\n" +
                                "    Sinon, conformément à l'article 2 de l'arrêté 462/04 portant règlement intérieur\n" +
                                "      de la Bibliothèque, passés les 2 mois de retard, vous recevrez une facture\n" +
                                "      correspondant à la valeur des documents non rendus.\n" +
                                "    Le règlement devra s'effectuer directement auprès de la Bibliothèque (paiement par\n" +
                                "      chèque à l'ordre de M. le Trésorier Principal de Tours Municipale).\n" +
                                "\n" +
                                "    Comptant sur une régularisation rapide de votre situation et restant à votre\n" +
                                "      disposition pour tout renseignement relatif aux prêts, je vous prie d'agréer mes\n" +
                                "      sincères salutations.\n" +
                                "\n" +
                                "                                        La Directrice de la Bibliothèque.\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "    ------------------------------------------\n" +
                                "\n" +
                                "    Titre: " + livreReserveBean.getLibrairie().getTitre() + "\n" +
                                "    Auteur: " + livreReserveBean.getLibrairie().getAuteur() + " \n" +
                                "    A rendre avant " + formater.format(livreReserveBean.getDateFin()) + "\n" +
                                "    ------------------------------------------\n" +
                                "\n" +
                                "\n");
                        batchBean.setEmailTo(client.get().getEmail());
                        batchBean.setIdLocation(livreReserveBean.getId());
                        livreReserveBean.setMailEnvoye(true);
                        mlibrairieProxy.modifListeReserve(livreReserveBean);
                        mbatchProxy.saveListBatch(batchBean);
                        }


            }
        }
        return "redirect:/";
       }
}
