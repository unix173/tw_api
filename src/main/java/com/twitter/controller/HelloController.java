package com.twitter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ivsi on 8/28/2015.
 */
@Controller
@RequestMapping("/")
public class HelloController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String viewIndexPage() {
        return "index";
    }

}