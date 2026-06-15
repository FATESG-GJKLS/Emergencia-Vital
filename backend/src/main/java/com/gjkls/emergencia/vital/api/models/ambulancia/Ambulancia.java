package com.gjkls.emergencia.vital.api.models.ambulancia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_AMBULANCIAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ambulancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModeloAmbulancia modelo;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$", message="Placa mal formatada")
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmbulancia status;
}
