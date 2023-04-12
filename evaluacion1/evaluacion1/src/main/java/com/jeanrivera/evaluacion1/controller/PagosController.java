package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.entity.Pagos;
import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.services.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping("/calculos")
    public String mostrarCalculos(Model model) {
        pagosService.calculo();
        List<Pagos> calculos = pagosService.findAll();
        model.addAttribute("calculos", calculos);
        return "calculos";
    }

}
