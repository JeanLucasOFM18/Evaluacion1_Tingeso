package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/usuario")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping("/prueba")
    public ResponseEntity<String> pruebas(@RequestParam String codigo){
        return ResponseEntity.ok(pagosService.pruebas(codigo));
    }

}
