package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.despacho.TipoDespacho;

public record RegistroDespachoDTO(
        Long ocorrenciaId,
        Long equipeId,
        TipoDespacho tipoDespacho) {
}