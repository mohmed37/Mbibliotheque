package com.client.service;

import com.client.bean.UserBean;
import com.client.controller.ClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
    ClientController controller;


    @Override
    public UserBean getUserConnec() {
        Object objConnected = SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        if (objConnected instanceof UserDetails) {
            UserDetails connectedUser = (UserDetails) objConnected;
            UserBean userConnec= controller.findUserByUsername( connectedUser.getUsername());
            return userConnec;
        }
        return null;

    }
}
