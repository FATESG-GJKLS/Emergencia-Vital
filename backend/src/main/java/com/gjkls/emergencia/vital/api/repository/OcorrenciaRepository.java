package com.gjkls.emergencia.vital.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findAllByOrderByDataHoraAberturaDesc();
    List<Ocorrencia> findByDataHoraAberturaAfter(LocalDateTime minusHours);
}
