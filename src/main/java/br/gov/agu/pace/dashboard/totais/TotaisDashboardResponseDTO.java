package br.gov.agu.pace.dashboard.totais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotaisDashboardResponseDTO {
    private long totalPautas;
    private long totalAudiencias;
    private long totalOrgaosJulgadores;
}
