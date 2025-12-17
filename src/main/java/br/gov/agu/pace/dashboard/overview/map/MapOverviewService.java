package br.gov.agu.pace.dashboard.overview.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapOverviewService {

    private final MapOverviewRepository repository;

    public MapOverviewResponseDTO getMapOverview(String view, Integer year, Integer month) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        if ("month".equalsIgnoreCase(view) && month != null) {
            return getMapOverviewPorMes(year, month);
        }
        return getMapOverviewPorAno(year);
    }

    public MapOverviewResponseDTO getMapOverviewPorAno(Integer ano) {
        List<MapCountProjection> dados = repository.countPautasEAudienciasPorUfAno(ano);
        return buildResponse(dados);
    }

    public MapOverviewResponseDTO getMapOverviewPorMes(Integer ano, Integer mes) {
        List<MapCountProjection> dados = repository.countPautasEAudienciasPorUfMes(ano, mes);
        return buildResponse(dados);
    }

    private MapOverviewResponseDTO buildResponse(List<MapCountProjection> dados) {
        List<MapDataDTO> dataList = dados.stream()
                .map(d -> MapDataDTO.builder()
                        .uf(d.getUf())
                        .audiencias(d.getAudiencias())
                        .pautas(d.getPautas())
                        .build())
                .collect(Collectors.toList());

        return MapOverviewResponseDTO.builder()
                .data(dataList)
                .build();
    }
}

