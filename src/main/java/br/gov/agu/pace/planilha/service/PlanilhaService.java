package br.gov.agu.pace.planilha.service;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.contestacao.ContestacaoService;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.planilha.dtos.PlanilhaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanilhaService {

    private final ExcelReaderService excelReaderService;
    private final ContestacaoService contestacaoService;
    private final TokenService tokenService;
    private final PautaService pautaService;

    public PlanilhaDTO importarPlanilha(MultipartFile file, String token) throws Exception {

        Set<AudienciaDTO> audiencias = excelReaderService.importarPlanilha(file);

        processarAudiencias(token, audiencias);

        var pautas = pautaService.agruparAudienciasPorPauta(audiencias);

        return PlanilhaDTO.builder()
                .message("Planilha importada com sucesso")
                .file(file.getOriginalFilename())
                .totalAudiencias(audiencias.size())
                .totalPautas(pautas.size())
                .build();
    }

    @Async
    protected void processarAudiencias(String token, Set<AudienciaDTO> audiencias) {

        for (AudienciaDTO audiencia : audiencias) {
            token = tokenService.renovarTokenSeExpirado(token);
            audiencia = contestacaoService.adicionarTipoContestacaoEProcessoId(audiencia, token);
        }

        pautaService.salvarPautas(audiencias);
    }


}
