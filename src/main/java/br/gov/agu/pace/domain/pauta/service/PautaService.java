package br.gov.agu.pace.domain.pauta.service;

import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PautaService {

    private Map<PautaDTO, List<AudienciaDTO>> agruparAudienciasPorPauta(Set<AudienciaDTO> audiencias) {
        return audiencias.stream()
                .collect(Collectors.groupingBy(
                        a -> new PautaDTO(a.getData(), a.getTurno(), a.getSala(), a.getOrgaoJulgador(), a.getUf())
                ));
    }

    public void salvarPautas(Set<AudienciaDTO> audienciasDTO){

        agruparAudienciasPorPauta(audienciasDTO).forEach((pauta, audiencias) -> {

        });
    }
}
