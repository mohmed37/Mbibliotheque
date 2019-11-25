package com.microserviceuser.web.controller;

import com.microserviceuser.dao.UserRepository;
import com.microserviceuser.entities.User;
import com.microserviceuser.web.exeception.ImpossibleAjouterUnUserException;
import com.microserviceuser.web.exeception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

  @Autowired
    UserRepository userRepository;

    @GetMapping("/admin")
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
    }

    @DeleteMapping(value = "/delete/{num}")
    public void deleteUser(@PathVariable("num")long num) {

        userRepository.deleteById(num);
    }

    @GetMapping(value = "/user/{num}")
    public Optional<User>getUser(@PathVariable("num") long num) {
        Optional<User> user = userRepository.findById(num);
        if(!user.isPresent()) throw new UserNotFoundException("Cet utilisateur n'existe pas");
        return user;
    }

    @GetMapping(name ="/users")
    public List<User> listUsers(){
        List<User>users=userRepository.findAll();
        return users;
    }

    @PostMapping(value = "/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User saveUser = userRepository.save(user);
        if(saveUser == null) throw new ImpossibleAjouterUnUserException("Impossible d'ajouter cet utilisateur");

        return new ResponseEntity<User>(saveUser, HttpStatus.CREATED);
    }
    /*
     Permet de mettre à jour l'etat d'un livre
    */
    @PutMapping(value = "/modif")
    public void updateUser(@RequestBody User user) {

        userRepository.save(user);

    }


}
