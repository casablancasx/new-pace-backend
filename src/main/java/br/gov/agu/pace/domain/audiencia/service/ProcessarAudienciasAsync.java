package br.gov.agu.pace.domain.audiencia.service;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.contestacao.ContestacaoService;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
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


    @Async
    public void processarAudiencias(String token, Set<AudienciaDTO> audiencias) {

        for (AudienciaDTO audiencia : audiencias) {
            token = tokenService.renovarTokenSeExpirado(token);
            audiencia = contestacaoService.adicionarTipoContestacaoEProcessoId(audiencia, token);
        }

        pautaService.salvarPautas(audiencias);
    }

}
