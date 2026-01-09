package br.gov.agu.pace.domain.unidade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;


    public UnidadeEntity salvarUnidade(Long unidadeId, String nomeUnidade){
       return unidadeRepository.save(new UnidadeEntity(unidadeId, nomeUnidade));
    }

}
