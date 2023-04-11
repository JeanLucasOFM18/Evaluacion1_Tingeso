package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.entity.Porcentaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorcentajeRepository extends JpaRepository<Porcentaje, Integer> {

    @Query(value = "SELECT p FROM Porcentaje p WHERE p.proveedor = :filtro")
    Porcentaje findByProveedor(@Param("filtro") String filtro);
}
