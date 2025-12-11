package br.gov.agu.pace.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSapiensApiResponse {

    private Long exp;

    private boolean passwordExpired;

    private Long timestamp;

    private String token;

    private String version;

}
