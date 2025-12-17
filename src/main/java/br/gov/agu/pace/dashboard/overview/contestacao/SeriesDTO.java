package br.gov.agu.pace.dashboard.overview.contestacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesDTO {

    private String name;
    private List<Long> data;
}
