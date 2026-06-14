package com.gjkls.emergencia.vital.api.dtos;

public record RegistroPacienteDTO(
        String nome,
        String CPF,
        String observacoes,
        Boolean anonimo) {
}