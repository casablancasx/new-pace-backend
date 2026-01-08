package br.gov.agu.pace.domain.user.pautista;


import br.gov.agu.pace.domain.user.dto.PautistaResponseDTO;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautista")
@RequiredArgsConstructor
public class PautistaController {

    private final PautistaService pautistaService;


    @PostMapping
    public ResponseEntity<PautistaResponseDTO> cadastrarPautista(@RequestBody UsuarioSapiensDTO dto, @RequestHeader("Authorization") String token){
        String cleanToken = token.replace("Bearer ","");
        var response = pautistaService.cadastrarPautista(dto,cleanToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PautistaResponseDTO>> listarPautista(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        var response = pautistaService.listarPautistas(page,size);
        return ResponseEntity.ok(response);
    }
}