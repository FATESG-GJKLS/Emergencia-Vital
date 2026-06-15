package com.gjkls.emergencia.vital.api.padroes.decorator;

import java.util.List;

//import org.springframework.stereotype.Repository;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import com.gjkls.emergencia.vital.api.repository.OcorrenciaRepository;

//@Repository
public class OcorrenciaRepositoryDecorator implements IOcorrenciaRepository {
    private final OcorrenciaRepository repository;
    private final ILogging logger = LoggerFactory.getLogger();

    public OcorrenciaRepositoryDecorator(
            OcorrenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ocorrencia save(Ocorrencia ocorrencia) {
        logger.printInfo("Salvando ocorrência: " + ocorrencia.getId());
        return repository.save(ocorrencia);
    }

    @Override
    public List<Ocorrencia> findAllByOrderByDataHoraAberturaDesc() {
        return repository.findAllByOrderByDataHoraAberturaDesc();
    }
}
