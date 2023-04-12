package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Pagos;
import com.jeanrivera.evaluacion1.entity.Porcentaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {

    @Query(value = "SELECT p FROM Pagos p WHERE p.codigo_proveedor = :filtro")
    Pagos findByCodigo_proveedor(@Param("filtro") String filtro);
}
