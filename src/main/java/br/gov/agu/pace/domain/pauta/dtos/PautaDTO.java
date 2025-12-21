package br.gov.agu.pace.domain.pauta.dtos;

import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.enums.Uf;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PautaDTO {

    private LocalDate data;

    private Turno turno;

    private String sala;

    private String orgaoJulgador;

    private Uf uf;

    public LocalDate getData() {
        return data;
    }
}
