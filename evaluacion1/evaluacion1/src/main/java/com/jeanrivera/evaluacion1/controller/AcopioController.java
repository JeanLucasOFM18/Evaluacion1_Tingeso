package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.services.AcopioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AcopioController {

    @Autowired
    private AcopioServices acopioServices;

    @GetMapping("/acopio")
    public String main() {
        return "acopio";
    }

    @PostMapping("/acopio")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        acopioServices.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        acopioServices.leerCsv("Acopio.csv");
        return "redirect:/acopio";
    }
}
