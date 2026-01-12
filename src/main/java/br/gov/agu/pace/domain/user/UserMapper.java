package br.gov.agu.pace.domain.user;

import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.user.dto.UsuarioResponseDTO;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import br.gov.agu.pace.integrations.dtos.UnidadeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UsuarioResponseDTO mapToResponse(UserEntity user) {
        List<SetorDTO> setoresDTO = user.getSetores() != null
                ? user.getSetores().stream()
                    .map(this::mapSetorToDTO)
                    .collect(Collectors.toList())
                : List.of();

        return new UsuarioResponseDTO(
                user.getSapiensId(),
                user.getNome(),
                user.getEmail(),
                user.getTelefone(),
                setoresDTO,
                user.getRole(),
                user.isContaAtiva()
        );
    }

    private SetorDTO mapSetorToDTO(SetorEntity setor) {
        UnidadeDTO unidadeDTO = setor.getUnidade() != null
                ? new UnidadeDTO(setor.getUnidade().getUnidadeId(), setor.getUnidade().getNome())
                : null;

        return new SetorDTO(
                setor.getSetorId(),
                setor.getNome(),
                unidadeDTO
        );
    }
}
