package com.cirt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class BasicController {
    
    @GetMapping
    public String getMethodName() {
        return "home";
    }
    
}
