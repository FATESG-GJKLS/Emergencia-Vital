package com.gjkls.emergencia.vital.api.padroes.template;

import java.time.Duration;
import java.time.LocalDateTime;

import com.gjkls.emergencia.vital.api.padroes.adapter.ILogging;
import com.gjkls.emergencia.vital.api.padroes.factory.LoggerFactory;

public abstract class TarefasTemplate {
    public final void execute() {
        LocalDateTime inicio = LocalDateTime.now();
        ILogging logger = LoggerFactory.getLogger();
        
        try {
            logger.printInfo("[" + getTarefaNome() + "] Iniciando execução");

            Object dados = coletarDados();

            registrar(dados);

            logger.printInfo(
                    "[" + getTarefaNome() + "] Finalizada com sucesso em " +
                            Duration.between(inicio, LocalDateTime.now()).toMillis() + "ms");

        } catch (Exception e) {
            logger.printError(
                    "[" + getTarefaNome() + "] Falha durante execução", e);
        }
    }

    protected abstract String getTarefaNome();

    protected abstract Object coletarDados();

    protected abstract void registrar(Object dados);
}