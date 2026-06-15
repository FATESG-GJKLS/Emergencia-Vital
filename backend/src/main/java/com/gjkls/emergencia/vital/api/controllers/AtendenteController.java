package com.gjkls.emergencia.vital.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gjkls.emergencia.vital.api.dtos.DespachoResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.EquipeResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.OcorrenciaResponseDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroDespachoDTO;
import com.gjkls.emergencia.vital.api.dtos.RegistroOcorrenciaDTO;
import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import com.gjkls.emergencia.vital.api.services.AtendenteService;

@RestController
@RequestMapping("/api/atendente")
public class AtendenteController {
    private final AtendenteService atendenteService;
    
    AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @PostMapping("/ocorrencias")
    public ResponseEntity<OcorrenciaResponseDTO> cadastrarOcorrencia(@RequestBody RegistroOcorrenciaDTO dto) {
        return ResponseEntity.ok(atendenteService.cadastrarOcorrencia(dto));
    }

    @GetMapping("/ocorrencias")
    public ResponseEntity<List<OcorrenciaResponseDTO>> listarOcorrencias() {
        return ResponseEntity.ok(atendenteService.listarOcorrencias());
    }

    @PostMapping("/ocorrencias/{idOcorrencia}")
    public ResponseEntity<OcorrenciaResponseDTO> encerrarOcorrencia(@PathVariable Long idOcorrencia) {
        return ResponseEntity.ok(atendenteService.encerrarOcorrencia(idOcorrencia));
    }

    @PostMapping("/despachos")
    public ResponseEntity<DespachoResponseDTO> cadastrarDespacho(@RequestBody RegistroDespachoDTO dto) {
        return ResponseEntity.ok(atendenteService.cadastrarDespacho(dto));
    }

    @GetMapping("/despachos")
    public ResponseEntity<List<DespachoResponseDTO>> listarDespachos() {
        return ResponseEntity.ok(atendenteService.listarDespachos());
    }

    @GetMapping("/equipes")
    public ResponseEntity<List<EquipeResponseDTO>> listarEquipes() {
        return ResponseEntity.ok(atendenteService.listarEquipes());
    }

    @GetMapping("/ambulancias")
    public ResponseEntity<List<Ambulancia>> listarAmbulancias() {
        return ResponseEntity.ok(atendenteService.listarAmbulancias());
    }
}
