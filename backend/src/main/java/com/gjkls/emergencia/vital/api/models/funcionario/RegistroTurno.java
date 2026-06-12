package com.gjkls.emergencia.vital.api.models.funcionario;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="TB_TURNOS")
public class RegistroTurno {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="funcionario_id", nullable=false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = true)
    private LocalDateTime dataHoraFim;
}
