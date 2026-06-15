package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.funcionario.TipoFuncionario;

public record RegistroFuncionarioDTO(String nome, String CPF, String email, String senha, TipoFuncionario tipoFuncionario) {
}
