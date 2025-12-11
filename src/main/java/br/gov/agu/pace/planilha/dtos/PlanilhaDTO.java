package br.gov.agu.pace.planilha.dtos;

import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanilhaDTO {

    private String message;
    private String file;
    private int totalAudiencias;
    private int totalPautas;
    private List<PautaDTO> pautas;
}
