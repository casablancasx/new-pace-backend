package br.gov.agu.pace.domain.enums;

public enum Turno {
    MANHA("Manhã"),
    TARDE("Tarde");

    private String descricao;

    Turno(String descricao) {
        this.descricao = descricao;
    }

    public static Turno fromString(String descricao) {
        for (Turno turno : Turno.values()) {
            if (turno.getDescricao().equalsIgnoreCase(descricao)) {
                return turno;
            }
        }
        throw new IllegalArgumentException("Turno desconhecido: " + descricao);
    }

    public String getDescricao() {
        return descricao;
    }
}
