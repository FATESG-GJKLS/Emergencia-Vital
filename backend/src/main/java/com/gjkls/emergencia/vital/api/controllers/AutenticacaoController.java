package com.gjkls.emergencia.vital.api.controllers;

import com.gjkls.emergencia.vital.api.configs.security.TokenService;
import com.gjkls.emergencia.vital.api.dtos.AutenticacaoDTO;
import com.gjkls.emergencia.vital.api.dtos.TokenResponseDTO;
import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AutenticacaoController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AutenticacaoDTO dto) {
        var senhaUsuario = new UsernamePasswordAuthenticationToken(dto.CPF(), dto.senha());
        var auth = this.authenticationManager.authenticate(senhaUsuario);
        var funcionario = (Funcionario) auth.getPrincipal();

        var token = tokenService.gerarToken(funcionario);
        return ResponseEntity.ok(new TokenResponseDTO(token, funcionario.getTipoFuncionario()));
    }
}
