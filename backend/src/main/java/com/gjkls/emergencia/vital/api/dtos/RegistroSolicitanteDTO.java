package com.gjkls.emergencia.vital.api.dtos;

public record RegistroSolicitanteDTO(
        String nome,
        String CPF,
        String telefone,
        Boolean anonimo) {
}