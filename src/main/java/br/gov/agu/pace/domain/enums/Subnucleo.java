package br.gov.agu.pace.domain.enums;

public enum Subnucleo {
    ESEAS,EBI,ETRU;


    public static Subnucleo getSubnucleo(String subnucleo) {
        switch (subnucleo) {
            case "ESEAS":
                return ESEAS;
            case "EBI":
                return EBI;
            case "ETRU":
                return ETRU;
            default:
                return null;
        }
    }
}
