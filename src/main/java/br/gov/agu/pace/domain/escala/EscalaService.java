package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscalaService {


    private final PautaRepository pautaRepository;

    public void escalarAvaliadores(EscalaRequestDTO data, String token){

        List<PautaEntity> pautasSemAvaliadores = pautaRepository.buscarPautasSemAvaliadoresEscalados(
                data.dataInicio(),
                data.dataFim(),
                data.ufs(),
                data.orgaoJulgadorIds(),
                data.tipoContestacao()
                );

        for (PautaEntity pauta : pautasSemAvaliadores) {

            
        }


    }
}
