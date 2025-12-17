package br.gov.agu.pace.domain.orgaoJulgador;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orgaoJulgador")
@RequiredArgsConstructor
public class OrgaoJulgadorController {

    private final OrgaoJulgadorService orgaoJulgadorService;


    @GetMapping
    public ResponseEntity<Page<OrgaoJulgadorEntity>> listarOrgaoJulgadores(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "false") String orderBy,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<OrgaoJulgadorEntity> orgaos = orgaoJulgadorService.listarOrgaosJulgadores(page, size, nome, orderBy,sort);
        return ResponseEntity.ok(orgaos);
    }
}
