package br.gov.agu.pace.domain.sala;

import lombok.RequiredArgsConstructor;
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


}
