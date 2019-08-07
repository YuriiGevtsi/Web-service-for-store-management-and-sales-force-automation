package com.diploma.cashregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class LogInController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {return "login";}

}
