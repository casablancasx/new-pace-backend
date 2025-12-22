package br.gov.agu.pace.domain.avaliador;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
