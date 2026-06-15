package com.gjkls.emergencia.vital.api.padroes.iterator;

import java.util.Iterator;
import java.util.Stack;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;

public class OcorrenciasIterator implements Iterator<Ocorrencia> {
    private Stack<Ocorrencia> ocorrencias = new Stack<>();
    private Iterator<Ocorrencia> it;

    public OcorrenciasIterator(Iterator<Ocorrencia> ocorrencias) {
        while (ocorrencias.hasNext()) {
            this.ocorrencias.push(ocorrencias.next());
        }
        this.it = this.ocorrencias.iterator();
    }

    @Override
    public Ocorrencia next() {
        return it.next();
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }
}