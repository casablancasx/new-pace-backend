package br.gov.agu.pace.domain.enums;

public enum Turno {
    MANHA("Manhã"),
    TARDE("Tarde");

    private String descricao;

    Turno(String descricao) {
        this.descricao = descricao;
    }

    private String getDescricao() {
        return descricao;
    }
}
