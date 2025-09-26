package com.example.api_pedidos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // diz pro Spring que essa classe é um controller REST
public class HealthCheckController {

    // esse metodo vai responder quando você acessar /ping
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
