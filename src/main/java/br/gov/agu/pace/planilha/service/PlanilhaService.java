package br.gov.agu.pace.planilha.service;

import br.gov.agu.pace.auth.service.TokenService;
import br.gov.agu.pace.contestacao.ContestacaoService;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.planilha.dtos.PlanilhaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanilhaService {

    private final ExcelReaderService excelReaderService;
    private final ContestacaoService contestacaoService;
    private final TokenService tokenService;

    public PlanilhaDTO importarPlanilha(MultipartFile file, String token) throws Exception {

        Set<AudienciaDTO> audiencias = excelReaderService.importarPlanilha(file);

        processarAudiencias(token, audiencias);

        return PlanilhaDTO.builder()
                .message("Planilha importada com sucesso")
                .file(file.getOriginalFilename())
                .totalAudiencias(audiencias.size())
                .totalPautas(1234)
                .build();
    }

    @Async
    protected void processarAudiencias(String token, Set<AudienciaDTO> audiencias) {

        for (AudienciaDTO audiencia : audiencias) {
            token = tokenService.renovarTokenSeExpirado(token);
            audiencia = contestacaoService.adicionarTipoContestacaoEProcessoId(audiencia, token);
        }
    }


}
