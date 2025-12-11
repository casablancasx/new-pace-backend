package br.gov.agu.pace.planilha.service;

import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.planilha.dtos.PlanilhaDTO;
import lombok.RequiredArgsConstructor;
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

    public PlanilhaDTO importarPlanilha(MultipartFile file, String token) throws Exception {

        Set<AudienciaDTO> audiencias = excelReaderService.importarPlanilha(file);

        List<PautaDTO> pautas = agruparAudienciasEmPautas(audiencias);

        return PlanilhaDTO.builder()
                .message("Planilha importada com sucesso")
                .file(file.getOriginalFilename())
                .totalAudiencias(audiencias.size())
                .totalPautas(pautas.size())
                .pautas(pautas)
                .build();
    }

    /**
     * Agrupa as audiências em pautas com base em: Data, Turno, Sala e OrgaoJulgador
     */
    private List<PautaDTO> agruparAudienciasEmPautas(Set<AudienciaDTO> audiencias) {
        Map<String, PautaDTO> pautasMap = new LinkedHashMap<>();

        for (AudienciaDTO audiencia : audiencias) {
            String chave = PautaDTO.gerarChave(
                    audiencia.getData(),
                    audiencia.getTurno(),
                    audiencia.getSala(),
                    audiencia.getOrgaoJulgador()
            );

            PautaDTO pauta = pautasMap.computeIfAbsent(chave, k ->
                    PautaDTO.builder()
                            .data(audiencia.getData())
                            .turno(audiencia.getTurno())
                            .sala(audiencia.getSala())
                            .orgaoJulgador(audiencia.getOrgaoJulgador())
                            .audiencias(new ArrayList<>())
                            .build()
            );

            pauta.getAudiencias().add(audiencia);
        }

        return new ArrayList<>(pautasMap.values());
    }
}
