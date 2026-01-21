package br.gov.agu.pace.domain.enums;

public enum Subnucleo {
    ESEAS,EBI,ERU;


    public static Subnucleo getSubnucleo(String subnucleo) {
        switch (subnucleo) {
            case "ESEAS":
                return ESEAS;
            case "EBI":
                return EBI;
            case "ERU":
                return ERU;
            default:
                return null;
        }
    }
}
