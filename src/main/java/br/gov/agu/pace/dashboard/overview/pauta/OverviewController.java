package br.gov.agu.pace.dashboard.overview.pauta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/overview")
@RequiredArgsConstructor
public class OverviewController {

    private final OverviewService overviewService;

    @GetMapping
    public ResponseEntity<PautaOverviewResponseDTO> getPautaOverview(
            @RequestParam(defaultValue = "year") String view,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {

        var response = overviewService.getPautaOverView(view,year,month);
        return ResponseEntity.ok(response);
    }

}
