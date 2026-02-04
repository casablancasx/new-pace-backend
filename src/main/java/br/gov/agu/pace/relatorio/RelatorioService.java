package br.gov.agu.pace.relatorio;

import br.gov.agu.pace.domain.audiencia.repository.AudienciaRepository;
import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.domain.escala.EscalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {


    private final EscalaRepository escalaRepository;
    private final AudienciaRepository audienciaRepository;


    public Page<EscalaRelatorioDTO> gerarRealatorioEscala(int page, int size, LocalDate dataInicio, LocalDate dataFim, Long userId, Long orgaoJulgadorId, TipoContestacao tipoContestacao, Subnucleo subnucleo, ClasseJudicial classeJudicial) {
        Pageable pageable = PageRequest.of(page, size);
        return escalaRepository.gerarRelatorioEscala(dataInicio,dataFim,userId,orgaoJulgadorId,tipoContestacao,subnucleo,classeJudicial,pageable);

    }

    public List<ContestacaoRelatorioDTO> gerarRelatorioContestacao(LocalDate dataInicio, LocalDate dataFim, Long userId, Long orgaoJulgadorId, TipoContestacao tipoContestacao, Subnucleo subnucleo, ClasseJudicial classeJudicial) {
        List<ContestacaoRelatorioDTO> resultado = audienciaRepository.gerarRelatorioContestacao(
                dataInicio,dataFim,userId,orgaoJulgadorId,tipoContestacao,subnucleo,classeJudicial
        );
        
        // Criar mapa com resultados obtidos
        Map<String, Long> mapaResultados = resultado.stream()
                .collect(Collectors.toMap(ContestacaoRelatorioDTO::getDescricao, ContestacaoRelatorioDTO::getTotal));
        
        // Obter todas as descrições do enum TipoContestacao
        List<String> todasAsDescricoes = Arrays.stream(TipoContestacao.values())
                .map(TipoContestacao::getDescricao)
                .collect(Collectors.toList());
        
        // Montar resultado com todos os tipos, preenchendo com 0 os que não têm valor
        return todasAsDescricoes.stream()
                .map(descricao -> new ContestacaoRelatorioDTO(descricao, mapaResultados.getOrDefault(descricao, 0L)))
                .collect(Collectors.toList());
    }

    public TotaisRelatorioDTO gerarRelatorioTotais(LocalDate dataInicio, LocalDate dataFim, Long userId, Long orgaoJulgadorId, TipoContestacao tipoContestacao, Subnucleo subnucleo, ClasseJudicial classeJudicial) {
        Long totalAudiencias = audienciaRepository.contarTotalAudiencias(
                dataInicio, dataFim, userId, orgaoJulgadorId, tipoContestacao, subnucleo, classeJudicial
        );
        Long totalPautas = audienciaRepository.contarTotalPautas(
                dataInicio, dataFim, userId, orgaoJulgadorId, tipoContestacao, subnucleo, classeJudicial
        );
        return new TotaisRelatorioDTO(totalAudiencias, totalPautas);
    }
}
