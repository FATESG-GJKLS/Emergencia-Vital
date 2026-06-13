package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.equipe.StatusEquipe;

import java.util.List;

public record EquipeResponseDTO(
        Long id,
        StatusEquipe status,
        Long ambulanciaId,
        List<Long> funcionarioIds
) {
}
