package com.microserviceuser.web.controller;

import com.microserviceuser.dao.AppRoleRepository;
import com.microserviceuser.dao.AppUserRepository;
import com.microserviceuser.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    private AppUserRepository appUserRepository;



    @PostMapping(value = "/users")
    public Optional<AppUser> findById(long id) {
        Optional<AppUser> appUsers = appUserRepository.findById(id);
        return appUsers;
    }


    @PostMapping(value = "/username")
    public AppUser findUserByUsername(@RequestParam(name = "username", defaultValue = "") String username) {
        return appUserRepository.findByUsername(username);
    }


}


