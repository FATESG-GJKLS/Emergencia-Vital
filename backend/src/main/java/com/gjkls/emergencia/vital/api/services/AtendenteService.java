package com.gjkls.emergencia.vital.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gjkls.emergencia.vital.api.dtos.DespachoResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.EquipeResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.OcorrenciaResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroDespachoDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroOcorrenciaDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroPacienteDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroSolicitanteDTO;
import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import com.gjkls.emergencia.vital.api.models.ambulancia.StatusAmbulancia;
import com.gjkls.emergencia.vital.api.models.despacho.Despacho;
import com.gjkls.emergencia.vital.api.models.despacho.TipoDespacho;
import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import com.gjkls.emergencia.vital.api.models.equipe.StatusEquipe;
import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;
import com.gjkls.emergencia.vital.api.models.ocorrencia.Paciente;
import com.gjkls.emergencia.vital.api.models.ocorrencia.Solicitante;
import com.gjkls.emergencia.vital.api.models.ocorrencia.StatusOcorrencia;
import com.gjkls.emergencia.vital.api.repository.AmbulanciaRepository;
import com.gjkls.emergencia.vital.api.repository.DespachoRepository;
import com.gjkls.emergencia.vital.api.repository.OcorrenciaRepository;
import com.gjkls.emergencia.vital.api.repository.EquipeRepository;

@Service
public class AtendenteService {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final DespachoRepository despachoRepository;
    private final EquipeRepository equipeRepository;
    private final AmbulanciaRepository ambulanciaRepository;

    AtendenteService(OcorrenciaRepository ocorrenciaRepository, DespachoRepository despachoRepository,
            EquipeRepository equipeRepository, AmbulanciaRepository ambulanciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.despachoRepository = despachoRepository;
        this.equipeRepository = equipeRepository;
        this.ambulanciaRepository = ambulanciaRepository;
    }

    @Transactional
    public OcorrenciaResponseDTO cadastrarOcorrencia(RegistroOcorrenciaDTO dto) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setEndereco(dto.endereco());
        ocorrencia.setBairro(dto.bairro());
        ocorrencia.setGravidade(dto.gravidade());
        ocorrencia.setDescricao(dto.descricao());

        if (dto.solicitante() == null) {
            throw new IllegalArgumentException("O solicitante da ocorrência é obrigatório.");
        }

        ocorrencia.setSolicitante(toSolicitante(dto.solicitante()));
        ocorrencia.setPacientes(dto.pacientes() == null ? List.of()
                : dto.pacientes().stream()
                        .map(AtendenteService::toPaciente)
                        .collect(Collectors.toList()));

        return toOcorrenciaResponse(ocorrenciaRepository.save(ocorrencia));
    }

    @Transactional
    public DespachoResponseDTO cadastrarDespacho(RegistroDespachoDTO dto) {
        if (dto.ocorrenciaId() == null || dto.equipeId() == null) {
            throw new IllegalArgumentException("Ocorrência e equipe são obrigatórias para criar um despacho.");
        }

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.ocorrenciaId())
                .orElseThrow(() -> new IllegalArgumentException("Ocorrência não encontrada."));
        if (ocorrencia.getStatus() == StatusOcorrencia.CONCLUIDA) {
            throw new IllegalStateException("A ocorrência precisa estar em aberto para receber um despacho.");
        }

        Equipe equipe = equipeRepository.findById(dto.equipeId())
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada."));
        if (equipe.getStatus() != StatusEquipe.DISPONIVEL) {
            throw new IllegalStateException("A equipe selecionada não está disponível.");
        }
        if (equipe.getAmbulancia() == null) {
            throw new IllegalStateException("A equipe selecionada não possui ambulância vinculada.");
        }
        Ambulancia ambulancia = equipe.getAmbulancia();
        if (ambulancia.getStatus() != StatusAmbulancia.ATRIBUIDA) {
            throw new IllegalStateException("A ambulância da equipe não está atribuída para uso.");
        }

        TipoDespacho tipoDespacho = dto.tipoDespacho() != null ? dto.tipoDespacho() : TipoDespacho.INICIAL;
        if (tipoDespacho == TipoDespacho.INICIAL && despachoRepository.existsByOcorrenciaIdAndTipoDespacho(
                        dto.ocorrenciaId(),
                        TipoDespacho.INICIAL)) {
            throw new IllegalStateException("Já existe um despacho inicial para esta ocorrência.");
        }

        Despacho despacho = new Despacho();
        despacho.setOcorrencia(ocorrencia);
        despacho.setEquipe(equipe);
        despacho.setTipoDespacho(tipoDespacho);
        despacho.setDataHoraDespacho(LocalDateTime.now());

        ocorrencia.setStatus(StatusOcorrencia.EM_ATENDIMENTO);
        equipe.setStatus(StatusEquipe.EM_ATENDIMENTO);
        ambulancia.setStatus(StatusAmbulancia.EM_ATENDIMENTO);

        ocorrenciaRepository.save(ocorrencia);
        equipeRepository.save(equipe);
        return toDespachoResponse(despachoRepository.save(despacho));
    }

    public List<OcorrenciaResponseDTO> listarOcorrencias() {
        return ocorrenciaRepository.findAllByOrderByDataHoraAberturaDesc().stream()
                .map(AtendenteService::toOcorrenciaResponse)
                .collect(Collectors.toList());
    }

    public static OcorrenciaResponseDTO toOcorrenciaResponse(Ocorrencia ocorrencia) {
        return new OcorrenciaResponseDTO(
                ocorrencia.getId(),
                ocorrencia.getDataHoraAbertura(),
                ocorrencia.getDataHoraEncerramento(),
                ocorrencia.getEndereco(),
                ocorrencia.getBairro(),
                ocorrencia.getGravidade(),
                ocorrencia.getStatus(),
                toSolicitanteDTO(ocorrencia.getSolicitante()),
                ocorrencia.getPacientes() == null ? List.of()
                        : ocorrencia.getPacientes().stream().map(AtendenteService::toPacienteDTO).collect(Collectors.toList()),
                ocorrencia.getDescricao());
    }

    public static EquipeResponseDTO toEquipeResponse(Equipe equipe) {
        if (equipe == null) {
            return null;
        }
        String ambulanciaInfo = equipe.getAmbulancia() != null 
            ? equipe.getAmbulancia().getPlaca() + " - " + equipe.getAmbulancia().getModelo() 
            : null;
        return new EquipeResponseDTO(
                equipe.getId(),
                equipe.getStatus(),
                equipe.getAmbulancia() != null ? equipe.getAmbulancia().getId() : null,
                ambulanciaInfo,
                equipe.getFuncionarios() != null
                        ? equipe.getFuncionarios().stream().map(f -> f.getId()).collect(Collectors.toList())
                        : List.of(),
                equipe.getFuncionarios() != null
                        ? equipe.getFuncionarios().stream().map(f -> f.getNome()).collect(Collectors.toList())
                        : List.of());
    }

    public static DespachoResponseDTO toDespachoResponse(Despacho despacho) {
        if (despacho == null) return null;
        Ocorrencia ocorrencia = despacho.getOcorrencia();
        Equipe equipe = despacho.getEquipe();

        return new DespachoResponseDTO(
                despacho.getId(),
                despacho.getDataHoraDespacho(),
                despacho.getDataHoraChegadaLocal(),
                despacho.getDataHoraFinalizacao(),
                despacho.getTipoDespacho(),
                despacho.getRelatorioFinalizacao(),
                toOcorrenciaResponse(ocorrencia),
                toEquipeResponse(equipe),
                despacho.getEquipe() != null && despacho.getEquipe().getAmbulancia() != null
                        ? despacho.getEquipe().getAmbulancia()
                        : null);
    }

    public static Solicitante toSolicitante(RegistroSolicitanteDTO dto) {
        Solicitante solicitante = new Solicitante();
        solicitante.setNome(dto.nome());
        solicitante.setCPF(dto.CPF());
        solicitante.setTelefone(dto.telefone());
        solicitante.setAnonimo(dto.anonimo() != null ? dto.anonimo() : Boolean.FALSE);
        return solicitante;
    }

    public static RegistroSolicitanteDTO toSolicitanteDTO(Solicitante solicitante) {
        if (solicitante == null) {
            return null;
        }
        return new RegistroSolicitanteDTO(
                solicitante.getNome(),
                solicitante.getCPF(),
                solicitante.getTelefone(),
                solicitante.getAnonimo());
    }

    public static Paciente toPaciente(RegistroPacienteDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setCPF(dto.CPF());
        paciente.setObservacoes(dto.observacoes());
        paciente.setAnonimo(dto.anonimo() != null ? dto.anonimo() : Boolean.FALSE);
        return paciente;
    }

    public static RegistroPacienteDTO toPacienteDTO(Paciente paciente) {
        return new RegistroPacienteDTO(
                paciente.getNome(),
                paciente.getCPF(),
                paciente.getObservacoes(),
                paciente.getAnonimo());
    }

    public OcorrenciaResponseDTO encerrarOcorrencia(Long idOcorrencia) {
        Optional<Ocorrencia> optionalOcorrencia = ocorrenciaRepository.findById(idOcorrencia);
        if (optionalOcorrencia.isEmpty()) {
            throw new IllegalArgumentException("Ocorrência não encontrada.");
        }
        Ocorrencia ocorrencia = optionalOcorrencia.get();
        if (ocorrencia.getStatus() == StatusOcorrencia.CONCLUIDA) {
            throw new IllegalStateException("A ocorrência já está encerrada.");
        }

        List<Despacho> despachos = despachoRepository.findByOcorrenciaId(idOcorrencia);

        if (despachos.stream().anyMatch(d -> d.getDataHoraFinalizacao() == null)) {
            throw new IllegalStateException(
                    "Não é possível encerrar a ocorrência enquanto houver despachos em atendimento.");
        }

        ocorrencia.setStatus(StatusOcorrencia.CONCLUIDA);
        ocorrencia.setDataHoraEncerramento(LocalDateTime.now());
        ocorrenciaRepository.save(ocorrencia);
        return toOcorrenciaResponse(ocorrencia);
    }

    public List<DespachoResponseDTO> listarDespachos() {
        return despachoRepository.findAllByOrderByDataHoraDespachoDesc().stream()
                .map(d -> {
                    Ocorrencia ocorrencia = d.getOcorrencia();
                    if (ocorrencia == null) {
                        return null;
                    }
                    return toDespachoResponse(d);
                })
                .filter(o -> o != null)
                .collect(Collectors.toList());
    }

    public List<EquipeResponseDTO> listarEquipes() {
        return equipeRepository.findAll().stream()
                .map(AtendenteService::toEquipeResponse)
                .collect(Collectors.toList());
    }

    public List<Ambulancia> listarAmbulancias() {
        return ambulanciaRepository.findAll();
    }
}
