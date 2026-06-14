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
import com.gjkls.emergencia.vital.api.dtos.RegistroFinalizacaoDespachoDTO;
import com.gjkls.emergencia.vital.api.services.ProfissionalService;

@RestController
@RequestMapping("/api/profissional")
public class ProfissionalController {
    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping("/despachos")
    public ResponseEntity<List<DespachoResponseDTO>> listarMeusDespachos() {
        return ResponseEntity.ok(profissionalService.listarMeusDespachos());
    }

    @PostMapping("/despachos/{idDespacho}/informar-chegada")
    public ResponseEntity<DespachoResponseDTO> informarChegadaLocal(@PathVariable Long idDespacho) {
        return ResponseEntity.ok(profissionalService.informarChegadaLocal(idDespacho));
    }
    
    @PostMapping("/despachos/{idDespacho}/finalizar")
    public ResponseEntity<DespachoResponseDTO> finalizarDespacho(
            @PathVariable Long idDespacho,
            @RequestBody RegistroFinalizacaoDespachoDTO dto) {
        return ResponseEntity.ok(profissionalService.finalizarDespacho(idDespacho, dto.relatorioFinalizacao()));
    }
}
