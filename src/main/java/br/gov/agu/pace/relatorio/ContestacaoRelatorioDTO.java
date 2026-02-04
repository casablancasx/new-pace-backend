package br.gov.agu.pace.relatorio;

import br.gov.agu.pace.domain.enums.TipoContestacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContestacaoRelatorioDTO {

    private String descricao;
    private Long total;

    public ContestacaoRelatorioDTO(TipoContestacao tipo, Long total) {
        this.descricao = tipo != null ? tipo.getDescricao() : null;
        this.total = total;
    }
}
