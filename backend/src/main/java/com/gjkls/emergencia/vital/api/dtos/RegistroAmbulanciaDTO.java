package com.gjkls.emergencia.vital.api.dtos;

import com.gjkls.emergencia.vital.api.models.ambulancia.ModeloAmbulancia;

public record RegistroAmbulanciaDTO(ModeloAmbulancia modelo, String placa) {
}
