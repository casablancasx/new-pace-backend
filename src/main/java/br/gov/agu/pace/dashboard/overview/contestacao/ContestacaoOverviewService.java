package br.gov.agu.pace.dashboard.overview.contestacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestacaoOverviewService {

    private final ContestacaoOverviewRepository repository;

    private static final List<String> TIPOS_CONTESTACAO = Arrays.asList(
            "TIPO1", "TIPO2", "TIPO3", "TIPO4", "TIPO5", "SEM_TIPO", "SEM_CONTESTACAO"
    );

    private static final List<String> CATEGORIAS_DISPLAY = Arrays.asList(
            "TIPO 1", "TIPO 2", "TIPO 3", "TIPO 4", "TIPO 5", "SEM TIPO", "SEM CONTESTAÇÃO"
    );

    public ContestacaoOverviewResponseDTO getContestacaoOverView(String view, Integer year, Integer month) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        if ("month".equalsIgnoreCase(view) && month != null) {
            return getContestacaoOverviewPorMes(year, month);
        }
        return getContestacaoOverviewPorAno(year);
    }

    public ContestacaoOverviewResponseDTO getContestacaoOverviewPorAno(Integer ano) {
        List<ContestacaoCountProjection> contestacoesAtual = repository.countContestacoesPorTipoAno(ano);
        List<ContestacaoCountProjection> contestacoesAnterior = repository.countContestacoesPorTipoAno(ano - 1);

        Map<String, Long> atualMap = contestacoesAtual.stream()
                .collect(Collectors.toMap(
                        ContestacaoCountProjection::getTipoContestacao,
                        ContestacaoCountProjection::getTotal,
                        (existing, replacement) -> existing
                ));

        Map<String, Long> anteriorMap = contestacoesAnterior.stream()
                .collect(Collectors.toMap(
                        ContestacaoCountProjection::getTipoContestacao,
                        ContestacaoCountProjection::getTotal,
                        (existing, replacement) -> existing
                ));

        List<Long> atualData = TIPOS_CONTESTACAO.stream()
                .map(tipo -> atualMap.getOrDefault(tipo, 0L))
                .collect(Collectors.toList());

        List<Long> anteriorData = TIPOS_CONTESTACAO.stream()
                .map(tipo -> anteriorMap.getOrDefault(tipo, 0L))
                .collect(Collectors.toList());

        SeriesDTO atualSeries = SeriesDTO.builder()
                .name("Atual (" + ano + ")")
                .data(atualData)
                .build();

        SeriesDTO anteriorSeries = SeriesDTO.builder()
                .name("Anterior (" + (ano - 1) + ")")
                .data(anteriorData)
                .build();

        return ContestacaoOverviewResponseDTO.builder()
                .categories(CATEGORIAS_DISPLAY)
                .series(Arrays.asList(atualSeries, anteriorSeries))
                .build();
    }

    public ContestacaoOverviewResponseDTO getContestacaoOverviewPorMes(Integer ano, Integer mes) {
        List<ContestacaoCountProjection> contestacoesAtual = repository.countContestacoesPorTipoMes(ano, mes);

        // Mês anterior
        int mesAnterior = mes == 1 ? 12 : mes - 1;
        int anoAnterior = mes == 1 ? ano - 1 : ano;
        List<ContestacaoCountProjection> contestacoesAnterior = repository.countContestacoesPorTipoMes(anoAnterior, mesAnterior);

        Map<String, Long> atualMap = contestacoesAtual.stream()
                .collect(Collectors.toMap(
                        ContestacaoCountProjection::getTipoContestacao,
                        ContestacaoCountProjection::getTotal,
                        (existing, replacement) -> existing
                ));

        Map<String, Long> anteriorMap = contestacoesAnterior.stream()
                .collect(Collectors.toMap(
                        ContestacaoCountProjection::getTipoContestacao,
                        ContestacaoCountProjection::getTotal,
                        (existing, replacement) -> existing
                ));

        List<Long> atualData = TIPOS_CONTESTACAO.stream()
                .map(tipo -> atualMap.getOrDefault(tipo, 0L))
                .collect(Collectors.toList());

        List<Long> anteriorData = TIPOS_CONTESTACAO.stream()
                .map(tipo -> anteriorMap.getOrDefault(tipo, 0L))
                .collect(Collectors.toList());

        String mesAtualFormatado = String.format("%02d/%d", mes, ano);
        String mesAnteriorFormatado = String.format("%02d/%d", mesAnterior, anoAnterior);

        SeriesDTO atualSeries = SeriesDTO.builder()
                .name("Atual (" + mesAtualFormatado + ")")
                .data(atualData)
                .build();

        SeriesDTO anteriorSeries = SeriesDTO.builder()
                .name("Anterior (" + mesAnteriorFormatado + ")")
                .data(anteriorData)
                .build();

        return ContestacaoOverviewResponseDTO.builder()
                .categories(CATEGORIAS_DISPLAY)
                .series(Arrays.asList(atualSeries, anteriorSeries))
                .build();
    }
}

