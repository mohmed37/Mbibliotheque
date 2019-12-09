package com.client.service;

import com.client.bean.RoleBean;
import com.client.bean.UserBean;
import com.client.proxies.MuserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccoutService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MuserProxy muserProxy;
    @Override
    public UserBean saveUser(UserBean userBean) {
        String passwordB = bCryptPasswordEncoder.encode(userBean.getPassword());
        userBean.setPassword(passwordB);

        return muserProxy.saveUser(userBean);
    }

    @Override
    public UserBean saveRole(RoleBean roleBean) {

        return muserProxy.saveRole(roleBean);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
      /*  UserBean userBean=muserProxy.findUserByUsername(username);
       RoleBean roleBean=muserProxy.findUserByRolename(rolename);
       userBean.getRoles().add(roleBean);*/



    }

    @Override
    public UserBean findUserByUsername(String username) {
//        return muserProxy.findUserByUsername(username);
        return null;
    }
    UserBean findUserbyUername(String username) {
        String passwordB = bCryptPasswordEncoder.encode("04101972");

        if(username.equalsIgnoreCase("admin")) {
            return new UserBean( "admin123", passwordB);
        }
        return null;
    }
}
