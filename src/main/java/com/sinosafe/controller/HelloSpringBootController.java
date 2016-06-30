package com.sinosafe.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Angus on Nov 8,2015.
 */
@RestController
@RequestMapping("/")
public class HelloSpringBootController {

    private final static Log log = LogFactory.getLog(HelloSpringBootController.class);



    @Transactional
    @RequestMapping("/")
    public String sayHello(){
        return "hello SpringBoot! --:";
    }

}
