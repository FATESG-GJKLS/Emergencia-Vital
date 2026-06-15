package com.gjkls.emergencia.vital.api.padroes.decorator;

import java.util.List;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;

public interface IOcorrenciaRepository {
    List<Ocorrencia> findAllByOrderByDataHoraAberturaDesc();
    Ocorrencia save(Ocorrencia ocorrencia);
}
