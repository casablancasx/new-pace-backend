package br.gov.agu.pace.domain.audiencia.service;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.planilha.service.ContestacaoService;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.planilha.entity.PlanilhaEntity;
import br.gov.agu.pace.domain.planilha.repository.PlanilhaRepository;
import br.gov.agu.pace.integrations.client.SapiensClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProcessarAudienciasAsync {

    private final ContestacaoService contestacaoService;
    private final PautaService pautaService;
    private final TokenService tokenService;
    private final PlanilhaRepository planilhaRepository;
    private final SapiensClient sapiensClient;


    @Async
    public void processarAudiencias(String token, Set<AudienciaDTO> audiencias, PlanilhaEntity planilha) throws JsonProcessingException {

        for (AudienciaDTO audiencia : audiencias) {
            token = tokenService.renovarTokenSeExpirado(token);
            audiencia = contestacaoService.adicionarTipoContestacaoEProcessoId(audiencia, token);
            Subnucleo subnucleo = sapiensClient.getSubnucleoFromProcesso(audiencia.getProcessoId(), token);
            audiencia.setSubnucleo(subnucleo);

            System.out.println(audiencia.getNumeroProcesso() + " - " + subnucleo + " - " + audiencia.getTipoContestacao());
        }
        planilha.setProcessamentoConcluido(true);
        planilhaRepository.save(planilha);

        pautaService.salvarPautas(audiencias);
    }

}
