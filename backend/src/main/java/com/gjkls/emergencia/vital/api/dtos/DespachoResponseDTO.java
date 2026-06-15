package com.gjkls.emergencia.vital.api.dtos;

import java.time.LocalDateTime;

import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import com.gjkls.emergencia.vital.api.models.despacho.TipoDespacho;

public record DespachoResponseDTO(
        Long id,
        LocalDateTime dataHoraDespacho,
        LocalDateTime dataHoraChegadaLocal,
        LocalDateTime dataHoraFinalizacao,
        TipoDespacho tipoDespacho,
        String relatorioFinalizacao,
        OcorrenciaResponseDTO ocorrencia,
        EquipeResponseDTO equipe,
        Ambulancia ambulancia) {
}