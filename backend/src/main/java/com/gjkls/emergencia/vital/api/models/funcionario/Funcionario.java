package com.gjkls.emergencia.vital.api.models.funcionario;

import jakarta.persistence.*;
import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
import java.util.Set;
import java.util.HashSet;

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
    @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$", message="CPF mal formatado")
    private String CPF;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*([._-][A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z]{2,})+$", message="Email mal formatado")
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

    @ManyToMany
    @JoinTable(name = "TB_EQUIPES_FUNCIONARIOS",
        joinColumns = @JoinColumn(name = "funcionario_id"),
        inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private Set<Equipe> equipes = new HashSet<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "equipe_ativa_id")
    private Equipe equipeAtiva;

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
