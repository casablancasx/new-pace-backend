package br.gov.agu.pace.dashboard.totais;

import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorRepository;
import br.gov.agu.pace.domain.pauta.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardTotalsService {

    private final PautaRepository pautaRepository;
    private final AudienciaRepository audienciaRepository;
    private final OrgaoJulgadorRepository orgaoJulgadorRepository;

    public TotaisDashboardResponseDTO getTotals() {
        long totalPautas = pautaRepository.count();
        long totalAudiencias = audienciaRepository.count();
        long totalOrgaosJulgadores = orgaoJulgadorRepository.count();
        return new TotaisDashboardResponseDTO(totalPautas, totalAudiencias, totalOrgaosJulgadores);
    }
}
