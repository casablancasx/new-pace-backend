package br.gov.agu.pace.domain.assunto;

import br.gov.agu.pace.domain.sala.SalaEntity;
import br.gov.agu.pace.domain.sala.SalaService;
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
public class AssuntoController {

    private final AssuntoService assuntoService;


    @GetMapping
    public ResponseEntity<Page<AssuntoEntity>> listarSalas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String orderBy,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var assuntos = assuntoService.listarAssuntos(page, size, nome, orderBy,sort);
        return ResponseEntity.ok(assuntos);
    }
}
