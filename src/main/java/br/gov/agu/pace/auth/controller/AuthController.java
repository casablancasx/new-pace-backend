package br.gov.agu.pace.auth.controller;

import br.gov.agu.pace.auth.dtos.LoginRequestDTO;
import br.gov.agu.pace.auth.dtos.LoginResponseDTO;
import br.gov.agu.pace.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO data){
        var response = authService.login(data);
        return ResponseEntity.ok(response);
    }
}
