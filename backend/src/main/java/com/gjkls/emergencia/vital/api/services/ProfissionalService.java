package com.gjkls.emergencia.vital.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gjkls.emergencia.vital.api.dtos.DespachoResponseDTO;
import com.gjkls.emergencia.vital.api.models.ambulancia.StatusAmbulancia;
import com.gjkls.emergencia.vital.api.models.despacho.Despacho;
import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import com.gjkls.emergencia.vital.api.models.equipe.StatusEquipe;
import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;
import com.gjkls.emergencia.vital.api.repository.DespachoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfissionalService {

    private final DespachoRepository despachoRepository;

    public ProfissionalService(DespachoRepository despachoRepository) {
        this.despachoRepository = despachoRepository;
    }

    public List<DespachoResponseDTO> listarMeusDespachos() {
        Funcionario funcionario = getFuncionarioAutenticado();
        Equipe equipeAtiva = funcionario.getEquipeAtiva();

        if (equipeAtiva == null) {
            return List.of();
        }

        return despachoRepository.findByEquipeId(equipeAtiva.getId()).stream().map(AtendenteService::toDespachoResponse)
                .toList();
    }

    @Transactional
    public DespachoResponseDTO informarChegadaLocal(Long idDespacho) {
        Funcionario funcionario = getFuncionarioAutenticado();

        Despacho despacho = despachoRepository.findById(idDespacho)
            .orElseThrow(() -> new IllegalArgumentException("Despacho não encontrado."));
        if (despacho.getEquipe() == null || despacho.getEquipe().getFuncionarios().stream().noneMatch(f -> Objects.equals(f.getId(), funcionario.getId()))) {
            throw new IllegalStateException("Este despacho não está atribuído à sua equipe.");
        }
        if (despacho.getDataHoraChegadaLocal() != null) {
            throw new IllegalStateException("A chegada ao local já foi informada para este despacho.");
        }

        despacho.setDataHoraChegadaLocal(LocalDateTime.now());
        return AtendenteService.toDespachoResponse(despachoRepository.save(despacho));
    }
    @Transactional
    public DespachoResponseDTO finalizarDespacho(Long idDespacho, String relatorioFinalizacao) {
        if (relatorioFinalizacao == null || relatorioFinalizacao.isBlank()) {
            throw new IllegalArgumentException("O relatório de finalização é obrigatório.");
        }

        Funcionario funcionario = getFuncionarioAutenticado();
        Equipe equipeAtiva = funcionario.getEquipeAtiva();

        if (equipeAtiva == null) {
            throw new IllegalStateException("O profissional não possui equipe ativa.");
        }

        Despacho despacho = despachoRepository.findById(idDespacho)
                .orElseThrow(() -> new IllegalArgumentException("Despacho não encontrado."));

        if (despacho.getEquipe() == null || despacho.getEquipe().getFuncionarios().stream().noneMatch(f -> Objects.equals(f.getId(), funcionario.getId()))) {
            throw new IllegalStateException("Este despacho não está atribuído à sua equipe.");
        }

        if (despacho.getDataHoraChegadaLocal() == null) {
            throw new IllegalStateException("A chegada ao local ainda não foi informada para este despacho.");
        }

        if (despacho.getDataHoraFinalizacao() != null) {
            throw new IllegalStateException("Este despacho já foi finalizado.");
        }

        despacho.setRelatorioFinalizacao(relatorioFinalizacao);
        despacho.setDataHoraFinalizacao(LocalDateTime.now());

        if (despacho.getEquipe() != null) {
            despacho.getEquipe().setStatus(StatusEquipe.DISPONIVEL);
            if (despacho.getEquipe().getAmbulancia() != null) {
                despacho.getEquipe().getAmbulancia().setStatus(StatusAmbulancia.ATRIBUIDA);
            }
        }

        return AtendenteService.toDespachoResponse(despachoRepository.save(despacho));
    }

    private Funcionario getFuncionarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Funcionario funcionario) {
            return funcionario;
        }
        throw new IllegalStateException("Usuário autenticado inválido.");
    }
}
