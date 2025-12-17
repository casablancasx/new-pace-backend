package br.gov.agu.pace.dashboard.overview.pauta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OverviewService {

    private final OverviewRepository repository;

    private static final List<String> MESES = Arrays.asList(
            "Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
            "Jul", "Ago", "Set", "Out", "Nov", "Dez"
    );

    public PautaOverviewResponseDTO getPautaOverView(String view, Integer year, Integer month) {
        if ("month".equalsIgnoreCase(view) && month != null) {
            return getPautasOverviewPorMes(year, month);
        }
        return getPautasOverviewPorAno(year);
    }

    public PautaOverviewResponseDTO getPautasOverviewPorAno(Integer ano) {
        List<MonthlyCountProjection> pautasPorMes = repository.countPautasPorMes(ano);
        List<MonthlyCountProjection> audienciasPorMes = repository.countAudienciasPorMes(ano);

        Map<Integer, Long> pautasMap = pautasPorMes.stream()
                .collect(Collectors.toMap(MonthlyCountProjection::getMes, MonthlyCountProjection::getTotal));

        Map<Integer, Long> audienciasMap = audienciasPorMes.stream()
                .collect(Collectors.toMap(MonthlyCountProjection::getMes, MonthlyCountProjection::getTotal));

        List<Integer> pautasData = new java.util.ArrayList<>();
        List<Integer> audienciasData = new java.util.ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            pautasData.add(pautasMap.getOrDefault(mes, 0L).intValue());
            audienciasData.add(audienciasMap.getOrDefault(mes, 0L).intValue());
        }

        SeriesDTO pautasSeries = SeriesDTO.builder()
                .name("Pautas")
                .data(pautasData)
                .build();

        SeriesDTO audienciasSeries = SeriesDTO.builder()
                .name("Audiências")
                .data(audienciasData)
                .build();

        return PautaOverviewResponseDTO.builder()
                .categories(MESES)
                .series(Arrays.asList(pautasSeries, audienciasSeries))
                .build();
    }

    public PautaOverviewResponseDTO getPautasOverviewPorMes(Integer ano, Integer mes) {
        List<DailyCountProjection> pautasPorDia = repository.countPautasPorDia(ano, mes);
        List<DailyCountProjection> audienciasPorDia = repository.countAudienciasPorDia(ano, mes);

        Map<Integer, Long> pautasMap = pautasPorDia.stream()
                .collect(Collectors.toMap(DailyCountProjection::getDia, DailyCountProjection::getTotal));

        Map<Integer, Long> audienciasMap = audienciasPorDia.stream()
                .collect(Collectors.toMap(DailyCountProjection::getDia, DailyCountProjection::getTotal));

        int diasNoMes = YearMonth.of(ano, mes).lengthOfMonth();
        String mesFormatado = String.format("%02d", mes);

        List<String> categories = new java.util.ArrayList<>();
        List<Integer> pautasData = new java.util.ArrayList<>();
        List<Integer> audienciasData = new java.util.ArrayList<>();

        for (int dia = 1; dia <= diasNoMes; dia++) {
            categories.add(String.format("%02d/%s", dia, mesFormatado));
            pautasData.add(pautasMap.getOrDefault(dia, 0L).intValue());
            audienciasData.add(audienciasMap.getOrDefault(dia, 0L).intValue());
        }

        SeriesDTO pautasSeries = SeriesDTO.builder()
                .name("Pautas")
                .data(pautasData)
                .build();

        SeriesDTO audienciasSeries = SeriesDTO.builder()
                .name("Audiências")
                .data(audienciasData)
                .build();

        return PautaOverviewResponseDTO.builder()
                .categories(categories)
                .series(Arrays.asList(pautasSeries, audienciasSeries))
                .build();
    }
}
