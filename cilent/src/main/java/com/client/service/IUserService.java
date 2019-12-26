package com.client.service;

import com.client.bean.LibrairieBean;
import com.client.bean.UserBean;

public interface IUserService {
    UserBean getUserConnec();
    void DatePasse(long id);

}
