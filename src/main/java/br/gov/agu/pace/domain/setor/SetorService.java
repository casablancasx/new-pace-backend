package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;


    public SetorEntity salvarSetor(Long setorId, String nomeSetor, UnidadeEntity unidade){
        SetorEntity setor = new SetorEntity(setorId, nomeSetor, unidade);
        return setorRepository.save(setor);
    }



}
