package com.gjkls.emergencia.vital.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gjkls.emergencia.vital.api.models.despacho.Despacho;
import com.gjkls.emergencia.vital.api.models.despacho.TipoDespacho;

public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    boolean existsByOcorrenciaIdAndTipoDespacho(Long ocorrenciaId, TipoDespacho inicial);

    List<Despacho> findByOcorrenciaId(Long idOcorrencia);
    List<Despacho> findByEquipeIdOrderByDataHoraDespachoDesc(Long id);
    List<Despacho> findAllByOrderByDataHoraDespachoDesc();

    List<Despacho> findByDataHoraDespachoAfter(LocalDateTime minusHours);
}