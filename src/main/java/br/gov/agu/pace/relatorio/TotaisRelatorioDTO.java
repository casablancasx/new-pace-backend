package br.gov.agu.pace.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotaisRelatorioDTO {

    private Long totalAudiencias;
    private Long totalPautas;
}
