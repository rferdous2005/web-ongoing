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
    
    @GetMapping("/who-we-are")
    public String getIntroWho() {
        return "basic/who";
    }

    @GetMapping("/what-we-do")
    public String getIntroWhat() {
        return "basic/what";
    }

    @GetMapping("/mission-vision")
    public String getIntroMisVis() {
        return "basic/mis-vis";
    }
    
}
