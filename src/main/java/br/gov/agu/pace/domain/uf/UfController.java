package br.gov.agu.pace.domain.uf;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uf")
@RequiredArgsConstructor
public class UfController {


    private final UfService ufService;


    @GetMapping
    public ResponseEntity<List<UfResponseDTO>> listarTodas() {
        List<UfResponseDTO> ufs = ufService.listarTodas();
        return ResponseEntity.ok(ufs);
    }
}
