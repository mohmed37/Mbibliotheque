package com.client.proxies;



import com.client.bean.LibrairieBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="zuul-server")
@RibbonClient("microservice-librairie")

public interface MlibrairieProxy {

    @GetMapping(value = "/microservice-librairie/librairies")
    List<LibrairieBean> listDesLivres(@RequestParam(name = "motClefAuteur",defaultValue ="") String motClefAuteur,
                                      @RequestParam(name = "motClefTitre",defaultValue ="") String motClefTitre/*,
                                      @RequestParam(name="page",defaultValue = "0")int page,
                                      @RequestParam(name="size",defaultValue = "5")int size*/
                                    );
    @GetMapping(value = "/microservice-librairie/librairies")
    List<LibrairieBean> listDesLivres1();

    @GetMapping(value = "/microservice-librairie/location")
    List<LibrairieBean> findByLocation(@RequestParam(name = "num") long num);


    @GetMapping(value = "/microservice-librairie/librairies/{id}")
    LibrairieBean recupererUnLivre(@PathVariable("id") int id);

    @PostMapping(value = "/microservice-librairie/librairies" )
    LibrairieBean saveLivre(@RequestBody LibrairieBean livre);

    @PutMapping(value = "/microservice-librairie/modif")
    LibrairieBean updatelivre(@RequestBody LibrairieBean librairie);

    @DeleteMapping(value = "/microservice-librairie/delete/{id}")
     LibrairieBean deletelivre(@PathVariable("id") Long id);

    @RequestMapping(value = "getPhoto",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] getPhoto(String id);
    }





