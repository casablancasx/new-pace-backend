package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.user.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {



    public UsuarioResponseDTO mapToResponse(UserEntity user){
        return new UsuarioResponseDTO(
                user.getSapiensId(),
                user.getNome(),
                user.getEmail(),
                user.getTelefone(),
                user.getSetor().getNome(),
                user.getSetor().getUnidade().getNome(),
                user.getRole(),
                user.isContaAtiva()
        );
    }
}
