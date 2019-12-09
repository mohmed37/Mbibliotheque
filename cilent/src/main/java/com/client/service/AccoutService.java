package com.client.service;

import com.client.bean.RoleBean;
import com.client.bean.UserBean;

public interface AccoutService {
    UserBean saveUser(UserBean userBean);
    UserBean saveRole(RoleBean roleBean);
    void addRoleToUser(String usermane,String rolename);
    UserBean findUserByUsername(String username);

}
