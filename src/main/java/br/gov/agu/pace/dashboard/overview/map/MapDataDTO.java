package br.gov.agu.pace.dashboard.overview.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapDataDTO {

    private String uf;
    private Long audiencias;
    private Long pautas;
}

