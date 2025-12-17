package br.gov.agu.pace.dashboard.overview.pauta;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DataInfomacoesDTO {

    private int ano;

    private List<String> meses;

}
