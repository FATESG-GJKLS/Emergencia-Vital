package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.funcionario.TipoFuncionario;

public record TokenResponseDTO(String token, TipoFuncionario tipoFuncionario) {
}
