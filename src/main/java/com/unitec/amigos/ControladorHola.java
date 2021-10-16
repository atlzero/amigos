package com.unitec.amigos;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorHola {


    //Este primer recurso es tu hola mundo de un servicio REST que usa el metodo
    //GET
    @GetMapping("/hola")
    public String saludar() {

        return " Hola desde mi primer servicio REST";
    }

}