package com.jeanrivera.evaluacion1.controller;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.services.ProveedorServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

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
    public String crearProveedor(@ModelAttribute Proveedor proveedor){
        //System.out.println(proveedor);
        if(proveedorServices.findByCodigo(proveedor.getCodigo())){
            System.out.println("Ya existe este codigo asociado");
        }
        else {
            Proveedor proveedornew = proveedorServices.crearProveedor(proveedor);
            if(proveedornew!=null){
                System.out.println("Registro Exitoso");
            }
            else {
                System.out.println("Registro Fallido");
            }
        }
        return "redirect:/registro";
    }
}
