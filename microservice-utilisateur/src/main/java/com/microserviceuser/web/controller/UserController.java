package com.microserviceuser.web.controller;

import com.microserviceuser.dao.AppRoleRepository;
import com.microserviceuser.dao.AppUserRepository;
import com.microserviceuser.entities.AppRole;
import com.microserviceuser.entities.AppUser;
import com.microserviceuser.web.exeception.ImpossibleAjouterUnUserException;
import com.microserviceuser.web.exeception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;

  /*  @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }

    @GetMapping("/guest")
    public String guest() {
        return "Hello Guest!";
    }*/

   /* @DeleteMapping(value = "/delete/{num}")
    public void deleteUser(@PathVariable("num")long num) {

        appUserRepository.deleteById(num);
    }

    @GetMapping(value = "/user/{num}")
    public Optional<AppUser>getUser(@PathVariable("num") long num) {
        Optional<AppUser> user = appUserRepository.findById(num);
        if(!user.isPresent()) throw new UserNotFoundException("Cet utilisateur n'existe pas");
        return user;
    }*/

    @GetMapping(value ="/users")
    public List<AppUser> listUsers(){
        List<AppUser> appUsers = appUserRepository.findAll();
        return appUsers;
    }



  /*  @PostMapping(value = "/saveUser")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser){
        AppUser saveAppUser = appUserRepository.save(appUser);
        if(saveAppUser == null) throw new ImpossibleAjouterUnUserException("Impossible d'ajouter cet utilisateur");

        return new ResponseEntity<AppUser>(saveAppUser, HttpStatus.CREATED);
    }
    *//*
     Permet de mettre à jour l'etat d'un livre
    *//*
    @PutMapping(value = "/modif")
    public void updateUser(@RequestBody AppUser appUser) {

        appUserRepository.save(appUser);

    }*/
  @PostMapping(value ="/saveUser")
  public AppUser saveUser(@RequestBody AppUser user) {
//      String hashPW= bCryptPasswordEncoder.encode(user.getPassword());
      user.setPassword(user.getPassword());

      return appUserRepository.save(user);
  }

    @PostMapping(value ="/saveRole")
    public AppRole saveRole(@RequestBody AppRole role) {

        return appRoleRepository.save(role);
    }

    @PostMapping(value ="/saveRoleUser")
    public void ddRoleToUser(String username, String roleName) {
        AppRole role=appRoleRepository.findByRole(roleName);
        AppUser user=appUserRepository.findByUsername(username);
        user.getRoles().add(role);

    }

    @PostMapping(value ="/username")
    public AppUser findUserByUsername(String username) {
     /*AppUser user =appUserRepository.findByUsername(username);
        String passwordB = bCryptPasswordEncoder.encode(user.getPassword());

        if(username.equalsIgnoreCase(user.getUsername())) {
            return new AppUser( user.getUsername(), passwordB);
        }
       *//* String passwordB = bCryptPasswordEncoder.encode("12345");
        if(username.equalsIgnoreCase("admin")) {
            return new AppUser( "admin123", passwordB);
        }*/

        return appUserRepository.findByUsername(username);
    }


    @PostMapping(value ="/saveRolename")
    public AppRole findRoleByUsername(String rolename) {

        return appRoleRepository.findByRole(rolename);
    }

}
