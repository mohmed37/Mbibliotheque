package com.client.proxies;

import com.client.bean.UserBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="microservice-utilisateur")
@RibbonClient(name = "microservice-utilisateur")
public interface MuserProxy {

    @GetMapping(value = "/users")
    List<UserBean> listUsers();

    @GetMapping(value = "/user/{num}")
    UserBean getUser(@PathVariable("num") int num);

    @PostMapping(value = "/saveUser" )
    ResponseEntity<UserBean> saveUser(@RequestBody UserBean user);

    @PutMapping(value = "/microservice-utilisateur/modif")
    UserBean updateUser(@RequestBody UserBean user);

    @DeleteMapping(value = "/microservice-utilisateur/delete/{num}")
    UserBean deleteUser(@PathVariable("num") Long num);
}
