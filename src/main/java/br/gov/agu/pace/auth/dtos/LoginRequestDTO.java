package br.gov.agu.pace.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @Email
    @NotBlank //Email RedeAgu
    private String username;

    @NotBlank
    private String password;

}
