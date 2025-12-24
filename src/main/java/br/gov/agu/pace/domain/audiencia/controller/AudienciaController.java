package br.gov.agu.pace.domain.audiencia.controller;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.audiencia.service.AudienciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audiencia")
@RequiredArgsConstructor
public class AudienciaController {

    private final AudienciaService audienciaService;


    @GetMapping
    public ResponseEntity<Page<AudienciaEntity>> listarAudiencias(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) String numeroProcesso,
            @RequestParam(required = false) Long orgaoJulgadorId,
            @RequestParam Sort.Direction sort,
            @RequestParam String orderBy
    ) {
        Page<AudienciaEntity> audiencias = audienciaService.listarAudiencias(page, size, numeroProcesso, orgaoJulgadorId, sort, orderBy);
        return ResponseEntity.ok(audiencias);
    }

    @PatchMapping
    public ResponseEntity<AudienciaEntity> atualizarAudiencia(
           @RequestBody AudienciaEntity audienciaAtualizada
    ) {
        AudienciaEntity audiencia = audienciaService.atualizarAudiencia(audienciaAtualizada);
        return ResponseEntity.ok(audiencia);
    }
}
