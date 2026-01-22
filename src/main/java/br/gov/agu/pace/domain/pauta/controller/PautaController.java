package br.gov.agu.pace.domain.pauta.controller;


import br.gov.agu.pace.domain.enums.Uf;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.pauta.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pauta")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;


    @GetMapping
    public ResponseEntity<Page<PautaEntity>> listarTodas(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "criadoEm") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sort,
            @RequestParam(required = false) Long orgaoJulgadorId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false)Uf uf
            ) {
        Page<PautaEntity> pautas = pautaService.listarTodas(page,size,orderBy, sort, orgaoJulgadorId,userId, uf);
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> buscarPautaPorId(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        PautaDTO pauta = pautaService.buscarPautaPorId(id, token);
        return ResponseEntity.ok(pauta);
    }
}
