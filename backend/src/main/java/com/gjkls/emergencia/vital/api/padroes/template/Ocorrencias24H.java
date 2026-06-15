package com.gjkls.emergencia.vital.api.padroes.template;

import java.time.LocalDateTime;
import java.util.Iterator;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;
import com.gjkls.emergencia.vital.api.models.ocorrencia.StatusOcorrencia;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import com.gjkls.emergencia.vital.api.padroes.iterator.OcorrenciasIterator;
import com.gjkls.emergencia.vital.api.repository.OcorrenciaRepository;

public class Ocorrencias24H extends TarefasTemplate {
    private final OcorrenciaRepository ocorrenciaRepository;
    private ILogging logger = LoggerFactory.getLogger();

    public Ocorrencias24H(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Override
    protected String getTarefaNome() {
        return "Checagem das Ocorrências em Aberto";
    }

    @Override
    protected Object coletarDados() {
        return new OcorrenciasIterator(ocorrenciaRepository.findByDataHoraAberturaAfter(
                LocalDateTime.now().minusHours(24)).iterator());
    }

    @Override
    protected void registrar(Object dados) {
        Iterator<Ocorrencia> iterator = (OcorrenciasIterator) dados;
        while (iterator.hasNext()) {
            Ocorrencia ocorrencia = iterator.next();
            if (ocorrencia.getStatus() != StatusOcorrencia.CONCLUIDA) {
                logger.printInfo("Ocorrência pendente: " + ocorrencia.getId());
            }
        }
    }
}
