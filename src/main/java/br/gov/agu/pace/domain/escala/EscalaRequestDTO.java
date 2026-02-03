package br.gov.agu.pace.domain.escala;

import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.enums.Uf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EscalaRequestDTO {

    private Long setorOrigemId;

    private Long especieTarefaId;

    private LocalDate dataInicio;

    private  LocalDate dataFim;

    private List<Uf> ufs;

    private List<TipoContestacao> tipoContestacao;

    private List<Long> orgaoJulgadorIds;

    private List<Long> avaliadorIds;

    private List<Long> pautistaIds;

    private boolean distribuicaoManualSetores;

    private long setorDestinoId;


}
