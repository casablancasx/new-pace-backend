package br.gov.agu.pace.domain.planilha.controller;

import br.gov.agu.pace.domain.planilha.dtos.PlanilhaDTO;
import br.gov.agu.pace.domain.planilha.entity.PlanilhaEntity;
import br.gov.agu.pace.domain.planilha.service.PlanilhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/planilha")
@RequiredArgsConstructor
public class PlanilhaController {

    private final PlanilhaService planilhaService;


    @PostMapping("/importar")
    public synchronized ResponseEntity<PlanilhaDTO> importarPlanilha(final @RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) throws Exception {
        token = token.replace("Bearer ", "");
        PlanilhaDTO response = planilhaService.importarPlanilha(file, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PlanilhaEntity>> listarPlanilhas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        Page<PlanilhaEntity> response = planilhaService.listarPlanilhas(page,size);
        return ResponseEntity.ok(response);
    }
}