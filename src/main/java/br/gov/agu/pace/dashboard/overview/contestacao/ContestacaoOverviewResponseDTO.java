package br.gov.agu.pace.dashboard.overview.contestacao;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContestacaoOverviewResponseDTO {

    private List<String> categories;
    private List<SeriesDTO> series;

}

