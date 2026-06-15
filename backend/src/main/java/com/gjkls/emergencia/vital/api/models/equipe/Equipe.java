package com.gjkls.emergencia.vital.api.models.equipe;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ManyToMany;

import com.gjkls.emergencia.vital.api.models.ambulancia.Ambulancia;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.gjkls.emergencia.vital.api.models.funcionario.Funcionario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_EQUIPES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(mappedBy = "equipes")
    private List<Funcionario> funcionarios = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StatusEquipe status;

    
    @ManyToOne(optional = true)
    @JoinColumn(name = "ambulancia_id")
    private Ambulancia ambulancia;
}
