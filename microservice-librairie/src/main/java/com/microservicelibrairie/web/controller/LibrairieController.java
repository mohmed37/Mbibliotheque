package com.microservicelibrairie.web.controller;

import com.microservicelibrairie.config.ApplicationLibrairieConfig;
import com.microservicelibrairie.dao.LibrairieRepository;
import com.microservicelibrairie.dao.LivreRepository;
import com.microservicelibrairie.dao.UserReservationDao;
import com.microservicelibrairie.entities.Librairie;
import com.microservicelibrairie.entities.LivreReserve;
import com.microservicelibrairie.entities.UserReservation;
import com.microservicelibrairie.web.exceptions.ImpossibleAjouterUnLivreException;
import com.microservicelibrairie.web.exceptions.LivreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
public class LibrairieController {


    @Autowired
    LibrairieRepository librairieRepository;
    @Autowired
    LivreRepository livreRepository;
    @Autowired
    UserReservationDao userReservationDao;

    @Value("${dir.images}")
    private String imageDir;
    @Autowired
    ApplicationLibrairieConfig appConfigs;

    @GetMapping(value = "/librairieAll")
    public List<Librairie>listeDesLivresAll(){
        return librairieRepository.findAll();
    }

    @GetMapping(value = "/userReservation")
    public  List<UserReservation>userReservations(){
        return userReservationDao.findAll();
    }


    @GetMapping(value = "/librairies")
    public List<Librairie> listDesLivres(@RequestParam(name="page",defaultValue = "0")Integer page,
                                         @RequestParam(name="size",defaultValue = "2")Integer size,
                                         @RequestParam(name = "motClefAuteur",defaultValue ="") String motClefAuteur,
                                         @RequestParam(name = "motClefTitre",defaultValue ="") String motClefTitre
                                        ) {
       List<Librairie>Pagelivres= librairieRepository.findByAuteurContainingIgnoreCaseAndTitreContainingIgnoreCase(
                motClefAuteur,motClefTitre,PageRequest.of(page,size,Sort.by("titre").descending()));

        return Pagelivres;


    }
    @GetMapping(value = "/location")
    public List<LivreReserve> findByLocation(@RequestParam(name = "num") long num){
        List<LivreReserve> livresLocation=livreRepository.findByIdClient(num) ;

           return livresLocation;

    }

    @GetMapping(value = "locationAll")
    public List<LivreReserve>livreReservesAll(){
        return livreRepository.findAll();
    }



    @GetMapping(value = "/librairies/{id}")
    public Optional<Librairie>recupererUnLivre(@PathVariable("id") long id){
        Optional<Librairie>livre=librairieRepository.findById(id);
        if(!livre.isPresent()) throw new LivreNotFoundException("Ce livre n'existe pas");
        return livre;
    }

    @PostMapping(value = "/librairies")
    public ResponseEntity<Librairie>saveLivre(@RequestBody Librairie livre){
        Librairie saveLivre = librairieRepository.save(livre);
        if(saveLivre == null) throw new ImpossibleAjouterUnLivreException("Impossible d'ajouter ce livre");

        return new ResponseEntity<Librairie>(saveLivre, HttpStatus.CREATED);
    }

//      Permet de mettre à jour l'etat d'un livre

    @PutMapping(value = "/librairies")
    public void updatelivre(@RequestBody Librairie livre) {
        librairieRepository.save(livre);

    }

    @DeleteMapping(value = "/librairies/{id}")
    public void deletelivre(@PathVariable("id") Long id){
        librairieRepository.deleteById(id);
    }

   @PutMapping(value ="/prolongation")
    public void prolongation(@RequestParam(name = "id") Long id) {
        LivreReserve prolongation= livreRepository.findById(id).get();

        Calendar cal = Calendar.getInstance();
        cal.setTime(prolongation.getDateFin());
        cal.add(Calendar.MONTH,1);
        prolongation.setDateFin(cal.getTime());
        prolongation.setProlongation(true);
        livreRepository.save(prolongation);
    }


    @PutMapping(value ="/modifListeReserve")
    public void modifListeReserve(@RequestBody LivreReserve livreReserve) {

        livreRepository.save(livreReserve);
    }

   /* @Scheduled(cron = "0 52 1 * * * ")
    public void miseJourStatus(){
        List<LivreReserve> listLivre = livreRepository.findAll();
        Date dateDuJour=new Date();

        for (int i = 0; i < listLivre.size(); i++) {
            long id = listLivre.get(i).getId();
            LivreReserve livre = livreRepository.findById(id).get();

            if(dateDuJour.after(livre.getDateFin())){
                livre.setRendreLivre(true);
            }else {
                livre.setRendreLivre(false);
            }
            livreRepository.save(livre);

        }



    }*/







}
