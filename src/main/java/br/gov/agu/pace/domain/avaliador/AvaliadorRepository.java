package br.gov.agu.pace.domain.avaliador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliadorRepository extends JpaRepository<AvaliadorEntity, Long> {


    @Query("SELECT a FROM AvaliadorEntity a WHERE a.sapiensId IN :ids")
    List<AvaliadorEntity> buscarAvaliadoresPorIds(List<Long> ids);
}
