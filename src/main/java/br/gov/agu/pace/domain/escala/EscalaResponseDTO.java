package br.gov.agu.pace.domain.escala;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class EscalaResponseDTO {
    private String message;
    private int sucesso;
    private int erro;
    private int total;
}
