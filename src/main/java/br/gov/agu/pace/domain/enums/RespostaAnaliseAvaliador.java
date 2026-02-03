package br.gov.agu.pace.domain.enums;

public enum RespostaAnaliseAvaliador {
    ANALISE_PENDENTE("Análise Pendente"),
    COMPARECER("Comparecer"),
    NAO_COMPARECER("Não Comparecer"),
    CANCELADA("Cancelada"),
    REDESIGNADA("Redesignada");

    private final String descricao;

    RespostaAnaliseAvaliador(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
