package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServices{

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public boolean findByCodigo(String codigo) {
        Proveedor proveedor = proveedorRepository.findByCodigo(codigo);
        if(proveedor == null){
            return false;
        }
        return true;
    }

    public Proveedor obtenerPorCodigo(String codigo) {
        Proveedor proveedor = proveedorRepository.findByCodigo(codigo);
        return proveedor;
    }

    public List<Proveedor> listadoProveedores() {
        return proveedorRepository.findAll();
    }
}
