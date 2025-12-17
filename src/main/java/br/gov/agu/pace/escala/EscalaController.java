package br.gov.agu.pace.escala;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escalar")
@RequiredArgsConstructor
public class EscalaController {

    private final EscalaService escalaService;


    @GetMapping("/avaliadores")
    public ResponseEntity<EscalaResponseDTO> escalarAvaliadores(@RequestBody EscalaRequestDTO data, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        var response = escalaService.escalarAvaliadores(data, token);
        return ResponseEntity.ok(response);
    }
}
