package br.gov.agu.pace.domain.planilha.repository;

import br.gov.agu.pace.domain.planilha.entity.PlanilhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanilhaRepository extends JpaRepository<PlanilhaEntity, Long> {
}
