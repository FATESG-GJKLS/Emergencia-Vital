package com.gjkls.emergencia.vital.api.configs;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gjkls.emergencia.vital.api.padroes.template.Despachos24H;
import com.gjkls.emergencia.vital.api.padroes.template.Ocorrencias24H;
import com.gjkls.emergencia.vital.api.padroes.template.TarefasTemplate;
import com.gjkls.emergencia.vital.api.repository.DespachoRepository;
import com.gjkls.emergencia.vital.api.repository.OcorrenciaRepository;

@Component
public class TarefasDiarias {
    private List<TarefasTemplate> tarefas = new LinkedList<>();

    public TarefasDiarias(OcorrenciaRepository ocorrenciaRepository, DespachoRepository despachoRepository) {
        tarefas.add(new Ocorrencias24H(ocorrenciaRepository));
        tarefas.add(new Despachos24H(despachoRepository));
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void executarTarefas() {
        for (TarefasTemplate tarefa : tarefas) {
            tarefa.execute();
        }
    }
}
