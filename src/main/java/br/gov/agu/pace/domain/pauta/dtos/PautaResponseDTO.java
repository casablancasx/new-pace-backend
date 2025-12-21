package br.gov.agu.pace.domain.pauta.dtos;

import br.gov.agu.pace.domain.audiencia.dtos.AudienciaResponseDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaResponseDTO extends PautaDTO{
    private Long pautaId;
    private List<AudienciaResponseDTO> audiencias;
}
