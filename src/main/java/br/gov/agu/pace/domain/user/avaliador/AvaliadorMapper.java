package br.gov.agu.pace.domain.user.avaliador;

import br.gov.agu.pace.domain.user.UserEntity;
import br.gov.agu.pace.domain.user.dto.AvaliadorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AvaliadorMapper {


    public AvaliadorResponseDTO mapToResponse(UserEntity avaliador){
        AvaliadorResponseDTO response = new AvaliadorResponseDTO();
        response.setId(avaliador.getSapiensId());
        response.setNome(avaliador.getNome());
        response.setEmail(avaliador.getEmail());
        response.setUnidade(avaliador.getSetor().getUnidade().getNome());
        response.setSetor(avaliador.getSetor().getNome());
        response.setQuantidadeAudiencias(avaliador.getMetric().getQuantidadeAudiencias());
        response.setQuantidadePautas(avaliador.getMetric().getQuantidadePautas());
        response.setQuantidadeAudienciasAvaliadas(avaliador.getMetric().getQuantidadeAudienciasAvaliadas());
        response.setDisponivel(avaliador.isDisponivel());
        return response;
    }
}
