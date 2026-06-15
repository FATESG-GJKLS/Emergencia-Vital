package com.gjkls.emergencia.vital.api.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.gjkls.emergencia.vital.api.models.ocorrencia.Bairro;
import com.gjkls.emergencia.vital.api.models.ocorrencia.GravidadeOcorrencia;
import com.gjkls.emergencia.vital.api.models.ocorrencia.StatusOcorrencia;

public record OcorrenciaResponseDTO(
	Long id,
	LocalDateTime dataHoraAbertura,
	LocalDateTime dataHoraEncerramento,
	String endereco,
	Bairro bairro,
	GravidadeOcorrencia gravidade,
	StatusOcorrencia status,
	RegistroSolicitanteDTO solicitante,
	List<RegistroPacienteDTO> pacientes,
	String descricao) {
}
