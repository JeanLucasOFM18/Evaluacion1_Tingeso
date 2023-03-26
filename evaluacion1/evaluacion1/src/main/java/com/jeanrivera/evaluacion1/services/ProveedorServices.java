package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Proveedor;

public interface ProveedorServices {

    public Proveedor crearProveedor(Proveedor proveedor);

    public boolean findByCodigo(String codigo);
}
