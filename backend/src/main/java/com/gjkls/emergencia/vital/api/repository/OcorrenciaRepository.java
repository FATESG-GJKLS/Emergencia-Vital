package com.gjkls.emergencia.vital.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    
}
