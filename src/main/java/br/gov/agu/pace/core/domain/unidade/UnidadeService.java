package br.gov.agu.pace.core.domain.unidade;

import br.gov.agu.pace.auth.dtos.SetorDTO;
import br.gov.agu.pace.core.domain.setor.SetorEntity;
import br.gov.agu.pace.core.domain.setor.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;


    public UnidadeEntity buscarOuCriarUnidadePorId(SetorDTO setorDTO){

        return unidadeRepository.findById(setorDTO.getSetorId()).orElseGet(
                () -> criarNovoSetor(setorDTO.getUnidadeId(), setorDTO.getNomeUnidade())
        );
    }


    private UnidadeEntity criarNovoSetor(Long unidadeId, String nome){
        return unidadeRepository.save(new UnidadeEntity(unidadeId,nome));
    }
}
