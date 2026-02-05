package br.gov.agu.pace.domain.user.avaliador;


import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.dto.AvaliadorResponseDTO;
import br.gov.agu.pace.domain.user.dto.UsuarioSapiensDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliador")
@RequiredArgsConstructor
public class AvaliadorController {

    private final AvaliadorService avaliadorService;


    @PostMapping
    public ResponseEntity<AvaliadorResponseDTO> cadastrarAvaliador(@RequestBody UsuarioSapiensDTO dto, @RequestHeader("Authorization") String token){
        String cleanToken = token.replace("Bearer ","");
        var response = avaliadorService.cadastrarAvaliador(dto,cleanToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AvaliadorResponseDTO>> listarAvaliadores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        var response = avaliadorService.listarAvaliadores(page,size);
        return ResponseEntity.ok(response);
    }
}