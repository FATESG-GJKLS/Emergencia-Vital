package com.gjkls.emergencia.vital.api.models.despacho;

import java.time.LocalDateTime;

import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import com.gjkls.emergencia.vital.api.models.ocorrencia.Ocorrencia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_DESPACHOS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraDespacho;

    @Column(nullable = true)
    private LocalDateTime dataHoraChegadaLocal;
    
    @Column(nullable = true)
    private LocalDateTime dataHoraFinalizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDespacho tipoDespacho;

    @Column(nullable = true)
    private String relatorioFinalizacao;

    @ManyToOne(optional = false)
    private Ocorrencia ocorrencia;

    @ManyToOne
    private Equipe equipe;

    @PrePersist
    public void prePersist() {
        this.dataHoraDespacho = LocalDateTime.now();
    }
}
