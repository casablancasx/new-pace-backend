package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;


    public SetorEntity buscarOuCriarSetor(Long setorId, String nomeSetor, UnidadeEntity unidade){

        return setorRepository.findById(setorId).orElseGet(
                () -> criarNovoSetor(setorId, nomeSetor, unidade)
        );
    }


    private SetorEntity criarNovoSetor(Long setorId, String nome, UnidadeEntity unidade){
        return setorRepository.save(new SetorEntity(setorId,nome,unidade));
    }
}
