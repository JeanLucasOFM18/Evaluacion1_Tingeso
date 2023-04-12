package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Acopio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AcopioRepository extends JpaRepository<Acopio, Integer> {

    @Query(value = "SELECT p FROM Acopio p WHERE p.proveedor = :filtro")
    List<Acopio> findByProveedor(@Param("filtro") String filtro);

    @Query(value = "SELECT DISTINCT p.fecha FROM Acopio p WHERE p.proveedor = :filtro")
    List<Date> findAllDistinctDates(@Param("filtro") String filtro);

    @Query(value = "SELECT p.fecha FROM Acopio p WHERE p.proveedor = :filtro")
    List<Date> findAllByFecha(@Param("filtro") String filtro);

    @Query("SELECT p.fecha FROM Acopio p WHERE p.proveedor = :proveedor AND p.turno = :turno")
    List<Date> contarTurnos(@Param("proveedor") String proveedor, @Param("turno") String turno);

}
