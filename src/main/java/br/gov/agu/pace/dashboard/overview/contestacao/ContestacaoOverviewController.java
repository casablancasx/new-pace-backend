package br.gov.agu.pace.dashboard.overview.contestacao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/contestacao-overview")
@RequiredArgsConstructor
public class ContestacaoOverviewController {

    private final ContestacaoOverviewService contestacaoOverviewService;

    @GetMapping
    public ResponseEntity<ContestacaoOverviewResponseDTO> getContestacaoOverview(
            @RequestParam(defaultValue = "year") String view,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        var response = contestacaoOverviewService.getContestacaoOverView(view, year, month);
        return ResponseEntity.ok(response);
    }

}

