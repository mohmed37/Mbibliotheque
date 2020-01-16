package com.microservicelibrairie.web.controller;

import com.microservicelibrairie.config.ApplicationLibrairieConfig;
import com.microservicelibrairie.dao.GenresRepository;
import com.microservicelibrairie.dao.LibrairieRepository;
import com.microservicelibrairie.dao.LivreRepository;
import com.microservicelibrairie.dao.UserReservationDao;
import com.microservicelibrairie.entities.Genre;
import com.microservicelibrairie.entities.Librairie;
import com.microservicelibrairie.entities.LivreReserve;
import com.microservicelibrairie.entities.UserReservation;
import com.microservicelibrairie.web.exceptions.GenreNotFoundException;
import com.microservicelibrairie.web.exceptions.ImpossibleAjouterUnLivreException;
import com.microservicelibrairie.web.exceptions.ImpossibleAjouterUneReservationException;
import com.microservicelibrairie.web.exceptions.LivreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class LibrairieController {


    @Autowired
    LibrairieRepository librairieRepository;
    @Autowired
    LivreRepository livreRepository;
    @Autowired
    UserReservationDao userReservationDao;
    @Autowired
    GenresRepository genresRepository;


    @Value("${dir.images}")
    private String imageDir;
    @Autowired
    ApplicationLibrairieConfig appConfigs;
    private Librairie livre;

    @GetMapping(value = "/librairieAll")
    public List<Librairie>listeDesLivresAll(){
        return librairieRepository.findAll();
    }

    @GetMapping(value = "/userReservation")
    public  List<UserReservation>userReservations(){
        return userReservationDao.findAll();
    }


    @GetMapping(value = "/librairies")
    public Page<Librairie> listDesLivres(@RequestParam(name="page",defaultValue = "0")int page,
                                         @RequestParam(name="size",defaultValue = "8")int size,
                                         @RequestParam(name = "motClefAuteur",defaultValue ="") String motClefAuteur,
                                         @RequestParam(name = "motClefTitre",defaultValue ="") String motClefTitre

                                        ) {
       Page<Librairie>Pagelivres= librairieRepository.findByAuteurContainingIgnoreCaseAndTitreContainingIgnoreCase(
                motClefAuteur,motClefTitre,PageRequest.of(page,size));

        return Pagelivres;


    }


    @GetMapping(value = "/location")
    public List<LivreReserve> findByLocation(@RequestParam(name = "num") long num){
        List<LivreReserve> livresLocation=livreRepository.findByIdClient(num) ;

           return livresLocation;

    }
    @GetMapping(value = "/genre")
    public List<Librairie> findByGenre(  @RequestParam(name = "genre",defaultValue =" " )String genre){
       List<Librairie>Genrelivres= librairieRepository.findByGenre_Genre(genre);

        return Genrelivres;

    }

    @GetMapping(value = "locationAll")
    public List<LivreReserve>livreReservesAll(){
        return livreRepository.findAll();
    }

    @GetMapping(value="genreAll")
    public  List<Genre>genreLivreAll(){
        return genresRepository.findAll();
    }

    @GetMapping(value = "/genre/{id}")
    public Optional<Genre>GenreLivre(@PathVariable("id") int id){
        Optional<Genre>genre=genresRepository.findById(id);
        if(!genre.isPresent()) throw new GenreNotFoundException("Ce genre de livre n'existe pas");
        return genre;
    }



    @GetMapping(value = "/librairie")
    public Optional<Librairie>recupererUnLivre(@RequestParam(name="id",defaultValue = " ")long id){
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


    @PostMapping(value ="saveReservation/{idLivre}/{idUser}" )
    public LivreReserve saveReservation(@RequestBody LivreReserve livreReserve, @PathVariable("idLivre") Long idLivre,
                                        @PathVariable("idUser") Long idUser)
           {
        Librairie livre= recupererUnLivre(idLivre).get();
        if (livre.getNExemplaire()<=0)throw new ImpossibleAjouterUneReservationException("Ce livre n'est plus" +
                " disponible");

        Date dateJour= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateJour);
        cal.add(Calendar.MONTH,1);

        List<UserReservation> list=userReservationDao.findAll();
        boolean userinscrit = false;

        for (int i =0; i < list.size(); i++){
            UserReservation userlist=list.get(i);

           if (userlist.getIdClient().compareTo(idUser)==0){
               userinscrit=true;
              }
           }
        if (userinscrit==false){
            UserReservation userReservation=new UserReservation();
            userReservation.setIdClient(idUser);
            userReservationDao.save(userReservation);
        }

               UserReservation userReservation= userReservationDao.findByIdClient(idUser);
               livreReserve.setDateDeb(dateJour);
               livreReserve.setDateFin(cal.getTime());
               livreReserve.setIdClient(idUser);
               livreReserve.setLibrairie(livre);
               livreReserve.setProlongation(false);
               livreReserve.setUserReservation(userReservation);
               livre.setNExemplaire(livre.getNExemplaire()-1);

               userReservation.setNbLivre(userReservation.getNbLivre()+1);

               return livreRepository.save(livreReserve);

    }
    @DeleteMapping (value ="deleteReservation/{id}" )
    public void deleteReservation(@PathVariable("id") Long id) {

        LivreReserve livreReserves=livreRepository.findById(id).get();
        Librairie livre= recupererUnLivre(livreReserves.getLibrairie().getId()).get();
        livre.setNExemplaire(livre.getNExemplaire()+1);
        livreReserves.getUserReservation().setNbLivre(livreReserves.getUserReservation().getNbLivre()-1);
        livreRepository.deleteById(id);

        if (livreReserves.getUserReservation().getNbLivre()==0){
            userReservationDao.delete(livreReserves.getUserReservation());
        }
    }











}
