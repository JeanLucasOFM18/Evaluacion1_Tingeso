package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.services.ProveedorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProveedorController {

    @Autowired
    private ProveedorServices proveedorServices;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @PostMapping("/crearProveedor")
    public String crearProveedor(@ModelAttribute Proveedor proveedor, RedirectAttributes redirectAttributes){
        //System.out.println(proveedor);
        if(proveedorServices.findByCodigo(proveedor.getCodigo())){
            redirectAttributes.addFlashAttribute("mensaje", "Ya esta registrado este codigo");
        }
        else {
            Proveedor proveedornew = proveedorServices.crearProveedor(proveedor);
            if(proveedornew!=null){
                redirectAttributes.addFlashAttribute("mensaje", "Registro Exitoso");
            }
            else {
                System.out.println("Registro Fallido");
            }
        }
        return "redirect:/registro";
    }

    @GetMapping("/proveedores")
    public String mostrarUsuarios(Model model) {
        List<Proveedor> proveedores = proveedorServices.listadoProveedores();
        model.addAttribute("proveedores", proveedores);
        return "proveedores";
    }


}
