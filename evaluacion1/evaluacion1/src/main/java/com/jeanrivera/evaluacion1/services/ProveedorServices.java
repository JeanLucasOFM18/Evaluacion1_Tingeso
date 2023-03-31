package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Proveedor;

import java.util.ArrayList;
import java.util.List;

public interface ProveedorServices {

    public Proveedor crearProveedor(Proveedor proveedor);

    public boolean findByCodigo(String codigo);
    public List<Proveedor> listadoProveedores();
}
