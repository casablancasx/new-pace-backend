package br.gov.agu.pace.domain.escala;


import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.enums.Uf;

import java.time.LocalDate;
import java.util.List;

public record EscalaRequestDTO(
        Long setorOrigemId,
        Long setorResponsavelId,
        Long especieTarefaId,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<Uf> ufs,
        List<TipoContestacao> tipoContestacao,
        List<Long> orgaoJulgadorIds,
        List<Long> avaliadorIds,
        List<Long> pautistaIds
) {
}
