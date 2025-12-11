package br.gov.agu.pace.planilha.controller;

import br.gov.agu.pace.planilha.dtos.PlanilhaDTO;
import br.gov.agu.pace.planilha.service.PlanilhaService;
import lombok.RequiredArgsConstructor;
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
}