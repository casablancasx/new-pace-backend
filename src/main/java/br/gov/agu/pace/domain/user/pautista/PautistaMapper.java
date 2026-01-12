package br.gov.agu.pace.domain.user.pautista;

import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.dto.PautistaResponseDTO;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import br.gov.agu.pace.integrations.dtos.UnidadeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PautistaMapper {

    public PautistaResponseDTO mapToResponse(UserEntity pautista) {
        List<SetorDTO> setoresDTO = pautista.getSetores() != null
                ? pautista.getSetores().stream()
                    .map(this::mapSetorToDTO)
                    .collect(Collectors.toList())
                : List.of();

        PautistaResponseDTO response = new PautistaResponseDTO();
        response.setId(pautista.getSapiensId());
        response.setNome(pautista.getNome());
        response.setEmail(pautista.getEmail());
        response.setSetores(setoresDTO);
        response.setQuantidadeAudiencias(pautista.getMetric().getQuantidadeAudiencias());
        response.setQuantidadePautas(pautista.getMetric().getQuantidadePautas());
        response.setDisponivel(pautista.isDisponivel());
        return response;
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
