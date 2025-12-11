package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;


    public SetorEntity buscarOuCriarSetorPorId(SetorDTO setorDTO){

        return setorRepository.findById(setorDTO.getSetorId()).orElseGet(
                () -> criarNovoSetor(setorDTO.getSetorId(), setorDTO.getNomeSetor())
        );
    }


    private SetorEntity criarNovoSetor(Long setorId, String nome){
        return setorRepository.save(new SetorEntity(setorId,nome));
    }
}
