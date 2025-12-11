package br.gov.agu.pace.domain.pauta.dtos;

import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.enums.Uf;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PautaDTO {

    private LocalDate data;

    private Turno turno;

    private String sala;

    private String orgaoJulgador;

    private Uf uf;

}
