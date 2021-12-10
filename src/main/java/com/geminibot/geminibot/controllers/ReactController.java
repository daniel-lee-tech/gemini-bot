package com.geminibot.geminibot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReactController {
    @GetMapping({"/", "/index"})
    public ModelAndView renderReactIndex() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/what")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
