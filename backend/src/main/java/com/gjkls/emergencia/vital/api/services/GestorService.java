package com.gjkls.emergencia.vital.api.services;

import com.gjkls.emergencia.vital.api.dtos.RegistroAmbulanciaDTO;
import com.gjkls.emergencia.vital.api.dtos.EquipeResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.FuncionarioResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroEquipeDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroFuncionarioDTO;
import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import com.gjkls.emergencia.vital.api.models.ambulancia.ModeloAmbulancia;
import com.gjkls.emergencia.vital.api.models.ambulancia.StatusAmbulancia;
import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import com.gjkls.emergencia.vital.api.models.equipe.StatusEquipe;
import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;
import com.gjkls.emergencia.vital.api.models.funcionario.TipoFuncionario;
import com.gjkls.emergencia.vital.api.repository.AmbulanciaRepository;
import com.gjkls.emergencia.vital.api.repository.EquipeRepository;
import com.gjkls.emergencia.vital.api.repository.FuncionarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GestorService {
	private final FuncionarioRepository funcionarioRepository;
	private final AmbulanciaRepository ambulanciaRepository;
	private final EquipeRepository equipeRepository;
	private final PasswordEncoder passwordEncoder;

	public GestorService(FuncionarioRepository funcionarioRepository,
			AmbulanciaRepository ambulanciaRepository,
			EquipeRepository equipeRepository,
			PasswordEncoder passwordEncoder) {
		this.funcionarioRepository = funcionarioRepository;
		this.ambulanciaRepository = ambulanciaRepository;
		this.equipeRepository = equipeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public FuncionarioResponseDTO cadastrarFuncionario(RegistroFuncionarioDTO dto) {
		Funcionario funcionario = new Funcionario();

		if (funcionarioRepository.existsByCPF(dto.CPF())) {
			throw new IllegalArgumentException("CPF já cadastrado.");
		}

		funcionario.setNome(dto.nome());
		funcionario.setCPF(dto.CPF());
		funcionario.setEmail(dto.email());
		funcionario.setSenha(passwordEncoder.encode(dto.senha()));
		funcionario.setTipoFuncionario(dto.tipoFuncionario());
		return toFuncionarioResponse(funcionarioRepository.save(funcionario));
	}

	@Transactional
	public Ambulancia cadastrarAmbulancia(RegistroAmbulanciaDTO dto) {
		Ambulancia ambulancia = new Ambulancia();
		ambulancia.setModelo(dto.modelo());
		ambulancia.setPlaca(dto.placa());
		ambulancia.setStatus(StatusAmbulancia.LIVRE);
		return ambulanciaRepository.save(ambulancia);
	}

	@Transactional
	public EquipeResponseDTO cadastrarEquipe(RegistroEquipeDTO dto) {
		Equipe equipe = new Equipe();
		equipe.setStatus(StatusEquipe.DISPONIVEL);

		if (dto.ambulanciaId() != null) {
			Optional<Ambulancia> ambulancia = ambulanciaRepository.findById(dto.ambulanciaId());
			if (ambulancia.isPresent()) {
				Ambulancia amb = ambulancia.get();
				if (amb.getStatus() != StatusAmbulancia.LIVRE) {
					throw new IllegalStateException("A ambulância selecionada não está disponível.");
				}
				amb.setStatus(StatusAmbulancia.ATRIBUIDA);
				equipe.setAmbulancia(amb);
			}
		} else {
			throw new IllegalArgumentException("Uma equipe deve ser associada a uma ambulância.");
		}

		Set<Funcionario> funcionarios = new HashSet<>();
		if (dto.funcionarioIds() != null) {
			for (Long id : dto.funcionarioIds()) {
				Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
				if (funcionario.isPresent()) {
					Funcionario f = funcionario.get();
					if (f.getAtivo() && (f.getTipoFuncionario() != TipoFuncionario.GESTOR) && (f.getTipoFuncionario() != TipoFuncionario.ATENDENTE)) {
						funcionarios.add(f);
					} else {
						throw new IllegalArgumentException(
								"O funcionário " + f.getNome() + " não pode ser alocado em uma equipe.");
					}
				}
			}
		}

		if (funcionarios.size() < 2) {
			throw new IllegalArgumentException("A equipe deve conter pelo menos 2 funcionários.");
		}

		long numCondutores = funcionarios.stream().filter(f -> f.getTipoFuncionario() == TipoFuncionario.CONDUTOR)
				.count();
		long numEnfermeiros = funcionarios.stream().filter(f -> f.getTipoFuncionario() == TipoFuncionario.ENFERMEIRO)
				.count();
		long numMedicos = funcionarios.stream().filter(f -> f.getTipoFuncionario() == TipoFuncionario.MEDICO).count();

		if (numCondutores < 1) {
			throw new IllegalArgumentException("A equipe deve conter pelo menos um condutor.");
		}

		if (equipe.getAmbulancia().getModelo() == ModeloAmbulancia.USA) {
			if (numEnfermeiros < 1 || numMedicos < 1) {
				throw new IllegalArgumentException(
						"A equipe para ambulância USA deve conter pelo menos um enfermeiro, um condutor e um médico.");
			}
		} else { // USB
			if ((numMedicos + numEnfermeiros) < 1) {
				throw new IllegalArgumentException(
						"A equipe para ambulância USB deve conter pelo menos um condutor e um médico ou enfermeiro.");
			}
		}

		// RD 06: Alocações simultâneas
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getEquipeAtiva() != null) {
				throw new IllegalStateException(
						"O funcionário " + funcionario.getNome() + " já está alocado em outra equipe ativa.");
			}
		}

		equipe.setFuncionarios(funcionarios.stream().collect(Collectors.toList()));
		Equipe equipeSalva = equipeRepository.save(equipe);

		for (Funcionario funcionario : funcionarios) {
			funcionario.getEquipes().add(equipeSalva);
			funcionario.setEquipeAtiva(equipeSalva);
			funcionarioRepository.save(funcionario);
		}

		return toEquipeResponse(equipeSalva);
	}

	private FuncionarioResponseDTO toFuncionarioResponse(Funcionario funcionario) {
		return new FuncionarioResponseDTO(
				funcionario.getId(),
				funcionario.getNome(),
				funcionario.getCPF(),
				funcionario.getEmail(),
				funcionario.getTipoFuncionario(),
				funcionario.getAtivo(),
				funcionario.getStatusTurno(),
				funcionario.getEquipeAtiva() != null ? funcionario.getEquipeAtiva().getId() : null,
				funcionario.getEquipes().stream().map(Equipe::getId).collect(Collectors.toList()));
	}

	private EquipeResponseDTO toEquipeResponse(Equipe equipe) {
		return new EquipeResponseDTO(
				equipe.getId(),
				equipe.getStatus(),
				equipe.getAmbulancia() != null ? equipe.getAmbulancia().getId() : null,
				equipe.getFuncionarios().stream().map(Funcionario::getId).collect(Collectors.toList()));
	}

	public List<FuncionarioResponseDTO> listarFuncionarios() {
		return funcionarioRepository.findAll().stream()
				.map(this::toFuncionarioResponse)
				.collect(Collectors.toList());
	}

	public List<Ambulancia> listarAmbulancias() {
		return ambulanciaRepository.findAll();
	}

	public List<EquipeResponseDTO> listarEquipes() {
		return equipeRepository.findAll().stream()
				.map(this::toEquipeResponse)
				.collect(Collectors.toList());
	}

	@Transactional
    public EquipeResponseDTO alterarStatusEquipe(Long id) {
		Optional<Equipe> equipeOpt = equipeRepository.findById(id);
		if (equipeOpt.isEmpty()) {
			throw new IllegalArgumentException("Equipe não encontrada.");
		}
		Equipe equipe = equipeOpt.get();
		if (equipe.getStatus() == StatusEquipe.EM_ATENDIMENTO) {
			throw new IllegalStateException("Não é possível alterar o status de uma equipe em atendimento.");
		}
		
		// se estivermos inativando a equipe, liberar a ambulância e os funcionários
		if (equipe.getStatus() == StatusEquipe.DISPONIVEL) {
			equipe.setStatus(StatusEquipe.INATIVA);
			if (equipe.getAmbulancia() != null) {
				equipe.getAmbulancia().setStatus(StatusAmbulancia.LIVRE);
				ambulanciaRepository.save(equipe.getAmbulancia());
			}
			for (Funcionario funcionario : equipe.getFuncionarios()) {
				funcionario.setEquipeAtiva(null);
				funcionarioRepository.save(funcionario);
			}
		}
		// em caso de reativação, a ambulancia tem que estar livre e a equipe também
		else if (equipe.getStatus() == StatusEquipe.INATIVA && equipe.getAmbulancia() != null) {
			if (equipe.getAmbulancia().getStatus() != StatusAmbulancia.LIVRE) {
				throw new IllegalStateException("A ambulância associada a esta equipe não está disponível.");
			}
			equipe.setStatus(StatusEquipe.DISPONIVEL);
			equipe.getAmbulancia().setStatus(StatusAmbulancia.ATRIBUIDA);
			ambulanciaRepository.save(equipe.getAmbulancia());

			for (Funcionario funcionario : equipe.getFuncionarios()) {
				if (funcionario.getEquipeAtiva() != null) {
					throw new IllegalStateException(
							"O funcionário " + funcionario.getNome() + " já está alocado em outra equipe ativa.");
				}
				funcionario.setEquipeAtiva(equipe);
				funcionarioRepository.save(funcionario);
			}
		}

		return toEquipeResponse(equipeRepository.save(equipe));
	}
}
