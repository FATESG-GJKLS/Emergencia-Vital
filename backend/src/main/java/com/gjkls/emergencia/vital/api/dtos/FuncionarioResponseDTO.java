package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.funcionario.StatusTurno;
import com.gjkls.emergencia.vital.api.models.funcionario.TipoFuncionario;

import java.util.List;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String CPF,
        String email,
        TipoFuncionario tipoFuncionario,
        Boolean ativo,
        StatusTurno statusTurno,
        Long equipeAtivaId,
        List<Long> equipeIds
) {
}
