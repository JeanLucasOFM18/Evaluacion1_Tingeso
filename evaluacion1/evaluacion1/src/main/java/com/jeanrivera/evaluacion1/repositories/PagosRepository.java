package com.jeanrivera.evaluacion1.repositories;

import com.jeanrivera.evaluacion1.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {
}
