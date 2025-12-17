package br.gov.agu.pace.dashboard.overview.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/map-overview")
@RequiredArgsConstructor
public class MapOverviewController {

    private final MapOverviewService mapOverviewService;

    @GetMapping
    public ResponseEntity<MapOverviewResponseDTO> getMapOverview(
            @RequestParam(defaultValue = "year") String view,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        var response = mapOverviewService.getMapOverview(view, year, month);
        return ResponseEntity.ok(response);
    }
}

