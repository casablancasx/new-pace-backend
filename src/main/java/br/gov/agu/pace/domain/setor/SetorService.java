package br.gov.agu.pace.domain.setor;

import br.gov.agu.pace.domain.unidade.UnidadeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;


    public SetorEntity salvarSetor(SetorEntity setor){
        return setorRepository.save(setor);
    }



}
