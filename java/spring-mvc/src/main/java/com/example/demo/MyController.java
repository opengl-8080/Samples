package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    
    @GetMapping("/hello")
    public String hello() {
        System.out.println("module = " + this.getClass().getModule());
        return "hello";
    }
}
