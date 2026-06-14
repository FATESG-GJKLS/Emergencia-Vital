package com.gjkls.emergencia.vital.api.configs;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;
import com.gjkls.emergencia.vital.api.models.ocorrencia.StatusOcorrencia;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import com.gjkls.emergencia.vital.api.padroes.iterator.OcorrenciasIterator;
import com.gjkls.emergencia.vital.api.repository.OcorrenciaRepository;

@Component
public class OcorrenciasTask {
    private final OcorrenciaRepository ocorrenciaRepository;
    private ILogging logger = LoggerFactory.getLogger();
    
    public OcorrenciasTask(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void checarOcorrencias() {
        OcorrenciasIterator iterator = new OcorrenciasIterator(ocorrenciaRepository.findAll().iterator());
        while (iterator.hasNext()) {
            Ocorrencia ocorrencia = iterator.next();
            if (ocorrencia.getStatus() != StatusOcorrencia.CONCLUIDA) {
                logger.printInfo("Ocorrência pendente: " + ocorrencia.getId());
            }
        }
    }
}
