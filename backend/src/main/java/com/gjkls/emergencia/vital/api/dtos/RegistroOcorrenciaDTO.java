package com.gjkls.emergencia.vital.api.dtos;

import java.util.List;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Bairro;
import com.gjkls.emergencia.vital.api.models.ocorrencia.GravidadeOcorrencia;

public record RegistroOcorrenciaDTO(
        String endereco,
        Bairro bairro,
        GravidadeOcorrencia gravidade,
        String descricao,
        RegistroSolicitanteDTO solicitante,
        List<RegistroPacienteDTO> pacientes) {
}