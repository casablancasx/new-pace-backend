package br.gov.agu.pace.domain.user.avaliador;

import br.gov.agu.pace.domain.setor.SetorEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.dto.AvaliadorResponseDTO;
import br.gov.agu.pace.integrations.dtos.SetorDTO;
import br.gov.agu.pace.integrations.dtos.UnidadeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliadorMapper {

    public AvaliadorResponseDTO mapToResponse(UserEntity avaliador) {
        List<SetorDTO> setoresDTO = avaliador.getSetores() != null
                ? avaliador.getSetores().stream()
                    .map(this::mapSetorToDTO)
                    .collect(Collectors.toList())
                : List.of();

        AvaliadorResponseDTO response = new AvaliadorResponseDTO();
        response.setId(avaliador.getSapiensId());
        response.setNome(avaliador.getNome());
        response.setEmail(avaliador.getEmail());
        response.setSetores(setoresDTO);
        response.setQuantidadeAudiencias(avaliador.getMetric().getQuantidadeAudiencias());
        response.setQuantidadePautas(avaliador.getMetric().getQuantidadePautas());
        response.setQuantidadeAudienciasAvaliadas(avaliador.getMetric().getQuantidadeAudienciasAvaliadas());
        response.setDisponivel(avaliador.isDisponivel());
        return response;
    }

    private SetorDTO mapSetorToDTO(SetorEntity setor) {
        UnidadeDTO unidadeDTO = setor.getUnidade() != null
                ? new UnidadeDTO(setor.getUnidade().getUnidadeId(), setor.getUnidade().getNome(), setor.getUnidade().getSigla())
                : null;

        return new SetorDTO(
                setor.getSetorId(),
                setor.getNome(),
                unidadeDTO
        );
    }
}
