package br.gov.agu.pace.domain.orgaoJulgador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrgaoJulgadorRepository extends JpaRepository<OrgaoJulgadorEntity, Long> {

    Optional<OrgaoJulgadorEntity> findByNome(String nome);
}
