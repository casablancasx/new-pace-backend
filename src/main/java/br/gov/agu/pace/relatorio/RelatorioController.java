package br.gov.agu.pace.relatorio;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Subnucleo;
import br.gov.agu.pace.domain.enums.TipoContestacao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioController {


    private final RelatorioService relatorioService;





    @GetMapping("/escala")
    public ResponseEntity<Page<EscalaRelatorioDTO>> listarAudiencias(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orgaoJulgadorId,
            @RequestParam(required = false) TipoContestacao tipoContestacao,
            @RequestParam(required = false)Subnucleo subnucleo,
            @RequestParam(required = false) ClasseJudicial classeJudicial
            ){

        Page<EscalaRelatorioDTO> response = relatorioService.gerarRealatorioEscala(page,size,dataInicio,dataFim,userId,orgaoJulgadorId,tipoContestacao,subnucleo,classeJudicial);


        return ResponseEntity.ok(response);
    }

    @GetMapping("/contestacao")
    public ResponseEntity<List<ContestacaoRelatorioDTO>> listarContestacoes(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orgaoJulgadorId,
            @RequestParam(required = false) TipoContestacao tipoContestacao,
            @RequestParam(required = false)Subnucleo subnucleo,
            @RequestParam(required = false) ClasseJudicial classeJudicial
    ){

        List<ContestacaoRelatorioDTO> response = relatorioService.gerarRelatorioContestacao(dataInicio,dataFim,userId,orgaoJulgadorId,tipoContestacao,subnucleo,classeJudicial);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/totais")
    public ResponseEntity<TotaisRelatorioDTO> listarTotais(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orgaoJulgadorId,
            @RequestParam(required = false) TipoContestacao tipoContestacao,
            @RequestParam(required = false)Subnucleo subnucleo,
            @RequestParam(required = false) ClasseJudicial classeJudicial
    ){
        TotaisRelatorioDTO response = relatorioService.gerarRelatorioTotais(
                dataInicio, dataFim, userId, orgaoJulgadorId, tipoContestacao, subnucleo, classeJudicial
        );
        return ResponseEntity.ok(response);
    }





}
