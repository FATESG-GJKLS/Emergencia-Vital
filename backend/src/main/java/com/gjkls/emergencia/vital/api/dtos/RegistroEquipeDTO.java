package com.gjkls.emergencia.vital.api.dtos;

import java.util.List;

public record RegistroEquipeDTO(Long ambulanciaId, List<Long> funcionarioIds) {
}
