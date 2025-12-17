package br.gov.agu.pace.dashboard.overview.pauta;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PautaOverviewResponseDTO {

    private List<String> categories;
    private List<SeriesDTO> series;

}
