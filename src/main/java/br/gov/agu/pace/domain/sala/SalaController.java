package br.gov.agu.pace.domain.sala;

import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sala")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;


    @GetMapping
    public ResponseEntity<Page<SalaEntity>> listarSalas(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var salas = salaService.listarSalas(page, size, nome, "nome");
        return ResponseEntity.ok(salas);
    }
}
