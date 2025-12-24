package br.gov.agu.pace.dashboard.totais;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/totais")
@RequiredArgsConstructor
public class DashboardTotalsController {

    private final DashboardTotalsService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TotaisDashboardResponseDTO> getTotals() {
        return ResponseEntity.ok(service.getTotals());
    }
}
