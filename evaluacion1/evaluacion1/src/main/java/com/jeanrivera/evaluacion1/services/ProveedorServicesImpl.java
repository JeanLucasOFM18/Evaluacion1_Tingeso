package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorServicesImpl implements ProveedorServices {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public boolean findByCodigo(String codigo) {
        Proveedor proveedor = proveedorRepository.findByCodigo(codigo);
        if(proveedor == null){
            return false;
        }
        return true;
    }

    @Override
    public List<Proveedor> listadoProveedores() {
        return proveedorRepository.findAll();
    }
}
