package br.gov.agu.pace.domain.uf;

import br.gov.agu.pace.domain.enums.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UfRepository extends JpaRepository<UfEntity, Integer> {

    Optional<UfEntity> findBySigla(Uf sigla);
}
