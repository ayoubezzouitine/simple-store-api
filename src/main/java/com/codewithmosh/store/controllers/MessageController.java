package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class MessageController {
     @GetMapping()
    public Message saysHello(){
        return new Message("Hello world, this is my amazing API");
    }
}
