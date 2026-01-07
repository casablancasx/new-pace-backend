package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;


    public SetorEntity buscarOuCriarSetor(Long setorId, String nomeSetor){

        return setorRepository.findById(setorId).orElseGet(
                () -> criarNovoSetor(setorId, nomeSetor)
        );
    }


    private SetorEntity criarNovoSetor(Long setorId, String nome){
        return setorRepository.save(new SetorEntity(setorId,nome));
    }
}
