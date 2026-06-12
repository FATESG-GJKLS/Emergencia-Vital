package com.gjkls.emergencia.vital.api.models.ocorrencia;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "TB_OCORRENCIAS")
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraAbertura;

    @Column(nullable = true)
    private LocalDateTime dataHoraEncerramento;

    @Column(nullable = false)
    @NotBlank
    private String endereco;
    
    @Enumerated(EnumType.STRING)
    private Bairro bairro;

    @Enumerated(EnumType.STRING)
    private GravidadeOcorrencia gravidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Solicitante solicitante;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ocorrencia_id", nullable = false)
    private List<Paciente> pacientes;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = StatusOcorrencia.ABERTA;
        }
    }
}
