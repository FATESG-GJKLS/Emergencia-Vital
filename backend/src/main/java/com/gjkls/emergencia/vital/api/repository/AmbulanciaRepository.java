package com.gjkls.emergencia.vital.api.repository;

import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanciaRepository extends JpaRepository<Ambulancia, Long> {
}
