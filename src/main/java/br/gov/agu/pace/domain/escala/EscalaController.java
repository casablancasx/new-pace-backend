package br.gov.agu.pace.domain.escala;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escalar")
@RequiredArgsConstructor
public class EscalaController {

    private final EscalaService escalaService;


    @PostMapping("/avaliadores")
    public ResponseEntity<EscalaResponseDTO> escalarAvaliadores(@RequestBody EscalaRequestDTO data, @RequestHeader("Authorization") String token) {
        String tokenLimpo  = token.replace("Bearer ", "");
        EscalaResponseDTO response = escalaService.escalarAvaliadores(data, tokenLimpo);
        return ResponseEntity.ok(response);
    }


//    @PostMapping("/pautistas")
//    public ResponseEntity<EscalaResponseDTO> post(@RequestBody EscalaRequestDTO dto, @RequestHeader("Authorization") String token) {
//        String tokenLimpo  = token.replace("Bearer ", "");
//        EscalaResponseDTO response = escalaService.escalarPautistas(dto, tokenLimpo);
//        return ResponseEntity.ok(response);
//    }
}
