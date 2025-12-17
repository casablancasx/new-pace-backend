package br.gov.agu.pace.domain.uf;

import br.gov.agu.pace.domain.enums.Uf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UfService {

    private final UfRepository repository;

    @Transactional
    public UfEntity buscarOuCriarUf(Uf sigla) {
        return repository.findBySigla(sigla)
                .orElseGet(() -> criarNovaUf(sigla));
    }

    private UfEntity criarNovaUf(Uf sigla) {
        UfEntity novaUf = new UfEntity();
        novaUf.setSigla(sigla);
        return repository.save(novaUf);
    }

    public List<UfResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(uf -> new UfResponseDTO(uf.getUfId(), uf.getSigla().name()))
                .toList();
    }
}
