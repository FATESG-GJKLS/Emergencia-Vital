package com.gjkls.emergencia.vital.api.models.ambulancia;

public enum StatusAmbulancia {
    LIVRE("Livre"),
    ATRIBUIDA("Atribuída"),
    EM_ATENDIMENTO("Em Atendimento"),
    INDISPONIVEL("Indisponível");

    private final String descricao;
    StatusAmbulancia(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return descricao;
    }
}
