package br.gov.agu.pace.domain.avaliador;


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
    public ResponseEntity<AvaliadorEntity> cadastrarAvaliador(@RequestBody AvaliadorRequestDTO dto){
        AvaliadorEntity response = avaliadorService.cadastrarAvaliador(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AvaliadorEntity>> listarAvaliadores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<AvaliadorEntity> response = avaliadorService.listarAvaliadores(page,size);
        return ResponseEntity.ok(response);
    }
}
