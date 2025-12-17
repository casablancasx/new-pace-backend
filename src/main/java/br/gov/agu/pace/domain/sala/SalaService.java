package br.gov.agu.pace.domain.sala;

import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;

    @Transactional
    public SalaEntity buscarSalaPorNome(String nome){
        return repository.findByNome(nome).orElseGet(() -> criarNovaSala(nome));
    }

    private SalaEntity criarNovaSala(String nome){
        return repository.save(new SalaEntity(nome));
    }


    public Page<SalaEntity> listarSalas(int page, int size, String nome, String orderBy, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), orderBy));
        return repository.buscarSalas(nome,pageable);
    }
}
