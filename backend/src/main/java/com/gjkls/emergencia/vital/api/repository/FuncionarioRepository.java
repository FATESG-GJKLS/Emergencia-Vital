package com.gjkls.emergencia.vital.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<UserDetails> findByCPFAndAtivo(String cpf, Boolean ativo);
}
