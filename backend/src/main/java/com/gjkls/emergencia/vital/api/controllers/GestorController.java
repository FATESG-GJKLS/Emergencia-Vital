package com.gjkls.emergencia.vital.api.controllers;

import com.gjkls.emergencia.vital.api.dtos.EdicaoAmbulanciaDTO;
import com.gjkls.emergencia.vital.api.dtos.EdicaoFuncionarioDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroAmbulanciaDTO;
import com.gjkls.emergencia.vital.api.dtos.DespachoResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.EquipeResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.FuncionarioResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.OcorrenciaResponseDTO;
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

	@PutMapping("/funcionarios/{id}")
	public ResponseEntity<FuncionarioResponseDTO> editarFuncionario(@PathVariable Long id, @RequestBody EdicaoFuncionarioDTO dto) {
		return ResponseEntity.ok(gestorService.editarFuncionario(id, dto));
	}

	@PostMapping("/ambulancias")
	public ResponseEntity<Ambulancia> cadastrarAmbulancia(@RequestBody RegistroAmbulanciaDTO dto) {
		return ResponseEntity.ok(gestorService.cadastrarAmbulancia(dto));
	}

    @GetMapping("/ambulancias")
    public ResponseEntity<List<Ambulancia>> listarAmbulancias() {
        return ResponseEntity.ok(gestorService.listarAmbulancias());
    }

	@PutMapping("/ambulancias/{id}")
	public ResponseEntity<Ambulancia> editarAmbulancia(@PathVariable Long id, @RequestBody EdicaoAmbulanciaDTO dto) {
		return ResponseEntity.ok(gestorService.editarAmbulancia(id, dto));
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
	@GetMapping("/ocorrencias")
	public ResponseEntity<List<OcorrenciaResponseDTO>> listarOcorrencias() {
		return ResponseEntity.ok(gestorService.listarOcorrencias());
	}

	@GetMapping("/despachos")
	public ResponseEntity<List<DespachoResponseDTO>> listarDespachos() {
		return ResponseEntity.ok(gestorService.listarDespachos());
	}
}
