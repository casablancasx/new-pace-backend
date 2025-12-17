package br.gov.agu.pace.dashboard.overview.map;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapOverviewResponseDTO {

    private List<MapDataDTO> data;
}

