package br.gov.agu.pace.domain.pauta.service;

import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PautaService {

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
