package br.gov.agu.pace.domain.user.pautista;

import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.dto.AvaliadorResponseDTO;
import br.gov.agu.pace.domain.user.dto.PautistaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PautistaMapper {


    public PautistaResponseDTO mapToResponse(UserEntity pautista){
        PautistaResponseDTO response = new PautistaResponseDTO();
        response.setId(pautista.getSapiensId());
        response.setNome(pautista.getNome());
        response.setEmail(pautista.getEmail());
        response.setUnidade(pautista.getSetor().getUnidade().getNome());
        response.setSetor(pautista.getSetor().getNome());
        response.setQuantidadeAudiencias(pautista.getMetric().getQuantidadeAudiencias());
        response.setQuantidadePautas(pautista.getMetric().getQuantidadePautas());
        response.setDisponivel(pautista.isDisponivel());
        return response;
    }
}
