package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.entity.Porcentaje;
import com.jeanrivera.evaluacion1.services.PorcentajeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PorcentajeController {

    @Autowired
    private PorcentajeServices porcentajeServices;

    @GetMapping("/porcentaje")
    public String main() {
        return "porcentaje";
    }

    @PostMapping("/porcentaje")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        porcentajeServices.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        porcentajeServices.leerCsv("Porcentajes.csv");
        return "redirect:/porcentaje";
    }

    /*
    @GetMapping("/fileInformation")
    public String listar(Model model) {
        List<Porcentaje> datas = porcentajeServices.obtenerData();
        model.addAttribute("datas", datas);
        return "fileInformation";
    }*/
}
