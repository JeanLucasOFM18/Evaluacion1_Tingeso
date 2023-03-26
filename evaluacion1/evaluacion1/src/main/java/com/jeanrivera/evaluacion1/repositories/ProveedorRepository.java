package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, String> {

    @Query(value = "SELECT p FROM Proveedor p WHERE p.codigo = :filtro")
    Proveedor findByCodigo(@Param("filtro") String filtro);
}
