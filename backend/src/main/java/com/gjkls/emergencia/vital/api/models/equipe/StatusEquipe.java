package com.gjkls.emergencia.vital.api.models.equipe;

public enum StatusEquipe {
    DISPONIVEL("Disponível"),
    EM_ATENDIMENTO("Em Atendimento"),
    INATIVA("Inativa");

    private final String descricao;
    StatusEquipe(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return descricao;
    }
}
