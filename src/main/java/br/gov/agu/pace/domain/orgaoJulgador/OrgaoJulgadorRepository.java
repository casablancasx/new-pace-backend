package br.gov.agu.pace.domain.orgaoJulgador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface OrgaoJulgadorRepository extends JpaRepository<OrgaoJulgadorEntity, Long> {

    Optional<OrgaoJulgadorEntity> findByNome(String nome);


    @Query("SELECT o FROM OrgaoJulgadorEntity o WHERE (:nome IS NULL OR LOWER(o.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<OrgaoJulgadorEntity> buscarOrgaoJulgadores(@Param("nome") String nome, Pageable pageable);
}
