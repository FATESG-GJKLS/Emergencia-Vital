package com.gjkls.emergencia.vital.api.padroes.template;

import java.time.LocalDateTime;
import java.util.List;

import com.gjkls.emergencia.vital.api.models.despacho.Despacho;
import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;
import com.gjkls.emergencia.vital.api.repository.DespachoRepository;

public class Despachos24H extends TarefasTemplate {
    private final DespachoRepository despachoRepository;
    private ILogging logger = LoggerFactory.getLogger();
    
    public Despachos24H(DespachoRepository despachoRepository) {
        this.despachoRepository = despachoRepository;
    }

    @Override
    protected String getTarefaNome() {
        return "Checagem dos Despachos em Andamento";
    }

    @Override
    protected Object coletarDados() {
        return despachoRepository.findByDataHoraDespachoAfter(LocalDateTime.now().minusHours(24));
    }

    @Override
    protected void registrar(Object dados) {
        @SuppressWarnings("unchecked")
        List<Despacho> despachos = (List<Despacho>) dados;
        despachos.forEach(despacho -> {
            if (despacho.getDataHoraFinalizacao() == null) {
                logger.printInfo("Despacho pendente: " + despacho.getId());
            }
        });
    }
}
