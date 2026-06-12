package com.gjkls.emergencia.vital.api.models.funcionario;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Table(name = "TB_FUNCIONARIOS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario implements UserDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Size(max=20)
    @Column(nullable = false, unique = true)
    private String CPF;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @NotNull
    @Enumerated(EnumType.STRING)
    TipoFuncionario tipoFuncionario;

    @Column(nullable = false)
    Boolean ativo;

    @NotNull
    @Enumerated(EnumType.STRING)
    StatusTurno statusTurno;

    @PrePersist
    public void prePersist() {
        this.ativo = true;
        this.statusTurno = StatusTurno.DESCONECTADO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoFuncionario == TipoFuncionario.ATENDENTE) {
            return List.of(new SimpleGrantedAuthority("ROLE_ATENDENTE"));
        }
        if (this.tipoFuncionario == TipoFuncionario.GESTOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_GESTOR"));
        }
        if (List.of(TipoFuncionario.MEDICO, TipoFuncionario.ENFERMEIRO, TipoFuncionario.CONDUTOR).contains(this.tipoFuncionario)) {
            return List.of(new SimpleGrantedAuthority("ROLE_PROFISSIONAIS_DA_SAUDE"));
        }
        return Collections.emptyList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getCPF();
    }
}
