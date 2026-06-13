package com.gjkls.emergencia.vital.api.repository;

import com.gjkls.emergencia.vital.api.models.equipe.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}
