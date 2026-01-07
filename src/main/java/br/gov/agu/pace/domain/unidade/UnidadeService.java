package br.gov.agu.pace.domain.unidade;

import br.gov.agu.pace.integrations.dtos.SetorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;


    public UnidadeEntity buscarOuCriarUnidade(Long unidadeId, String nomeUnidade){

        return unidadeRepository.findById(unidadeId).orElseGet(
                () -> criarNovoSetor(unidadeId, nomeUnidade)
        );
    }


    private UnidadeEntity criarNovoSetor(Long unidadeId, String nome){
        return unidadeRepository.save(new UnidadeEntity(unidadeId,nome));
    }
}
