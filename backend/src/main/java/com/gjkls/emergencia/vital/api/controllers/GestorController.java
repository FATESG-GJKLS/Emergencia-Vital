package com.gjkls.emergencia.vital.api.controllers;

import com.gjkls.emergencia.vital.api.dtos.RegistroAmbulanciaDTO;
import com.gjkls.emergencia.vital.api.dtos.EquipeResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.FuncionarioResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroEquipeDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroFuncionarioDTO;
import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import com.gjkls.emergencia.vital.api.services.GestorService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gestor")
public class GestorController {

	private final GestorService gestorService;

	public GestorController(GestorService gestorService) {
		this.gestorService = gestorService;
	}

	@PostMapping("/funcionarios")
	public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(@RequestBody RegistroFuncionarioDTO dto) {
		return ResponseEntity.ok(gestorService.cadastrarFuncionario(dto));
	}

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarFuncionarios() {
        return ResponseEntity.ok(gestorService.listarFuncionarios());
    }

	@PostMapping("/ambulancias")
	public ResponseEntity<Ambulancia> cadastrarAmbulancia(@RequestBody RegistroAmbulanciaDTO dto) {
		return ResponseEntity.ok(gestorService.cadastrarAmbulancia(dto));
	}

    @GetMapping("/ambulancias")
    public ResponseEntity<List<Ambulancia>> listarAmbulancias() {
        return ResponseEntity.ok(gestorService.listarAmbulancias());
    }

	@PostMapping("/equipes")
	public ResponseEntity<EquipeResponseDTO> cadastrarEquipe(@RequestBody RegistroEquipeDTO dto) {
		return ResponseEntity.ok(gestorService.cadastrarEquipe(dto));
	}

    @GetMapping("/equipes")
    public ResponseEntity<List<EquipeResponseDTO>> listarEquipes() {
        return ResponseEntity.ok(gestorService.listarEquipes());
    }

	@PutMapping("/equipes/{id}")
	public ResponseEntity<EquipeResponseDTO> alterarStatusEquipe(@PathVariable Long id) {
		return ResponseEntity.ok(gestorService.alterarStatusEquipe(id));
	}

	// TODO: Endpoint de dashboards
}
