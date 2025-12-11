package br.gov.agu.pace.domain.advogado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvogadoRepository extends JpaRepository<AdvogadoEntity, Long> {

    Optional<AdvogadoEntity> findByNome(String nome);

    List<AdvogadoEntity> findByNomeIn(List<String> nomes);
}
