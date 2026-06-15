package com.gjkls.emergencia.vital.api.models.ambulancia;

public enum ModeloAmbulancia {
    USA("Unidade de Suporte Avançado"),
    USB("Unidade de Suporte Básico");

    private final String descricao;
    ModeloAmbulancia(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return this.descricao;
    }
}
