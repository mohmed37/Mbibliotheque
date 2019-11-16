package com.clientiu.proxies;


import com.clientiu.bean.LibrairieBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservice-librairie", url = "localhost:9000")
public interface MlibrairieProxy {

    @GetMapping(value = "librairies")
    List<LibrairieBean> listDesLivres();

    @GetMapping(value = "librairies/{id}")
    LibrairieBean recupererUnLivre(@PathVariable("id") int id);

}
