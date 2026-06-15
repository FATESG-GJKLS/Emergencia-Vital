package com.gjkls.emergencia.vital.api.models.ocorrencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_SOLICITANTES")
public class Solicitante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private String nome;
    
    @Column(nullable = true)
    @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$", message="CPF mal formatado")
    private String CPF;

    @Column(nullable = true)
    @Pattern(regexp = "^\\([0-9]{2}\\)9[0-9]{4}-[0-9]{4}$", message="Telefone mal formatado")
    private String telefone;

    @Column(nullable = false)
    private Boolean anonimo;
}
