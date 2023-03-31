package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Acopio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcopioRepository extends JpaRepository<Acopio, Integer> {
}
