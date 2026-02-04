package br.gov.agu.pace.relatorio;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.enums.Turno;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class EscalaRelatorioDTO {

    private Long id;
    private LocalDate data;
    private String horario;
    private String numeroProcesso;
    private String nome;
    private String sala;
    private String orgaoJulgador;
    private ClasseJudicial classeJudicial;
    private Subnucleo subnucleo;
    private TipoContestacao tipoContestacao;
    private Turno turno;
    private RespostaAnaliseAvaliador analiseAvaliador;
    private String observacao;

    public EscalaRelatorioDTO(Long id, LocalDate data, String horario, String numeroProcesso,
                               String nome, String sala, String orgaoJulgador,
                               ClasseJudicial classeJudicial, Subnucleo subnucleo,
                               TipoContestacao tipoContestacao, Turno turno,
                               RespostaAnaliseAvaliador analiseAvaliador, String observacao) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.numeroProcesso = numeroProcesso;
        this.nome = nome;
        this.sala = sala;
        this.orgaoJulgador = orgaoJulgador;
        this.classeJudicial = classeJudicial;
        this.subnucleo = subnucleo;
        this.tipoContestacao = tipoContestacao;
        this.turno = turno;
        this.analiseAvaliador = analiseAvaliador;
        this.observacao = observacao;
    }
}
