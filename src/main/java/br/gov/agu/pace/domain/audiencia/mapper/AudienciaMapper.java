package br.gov.agu.pace.domain.audiencia.mapper;

import br.gov.agu.pace.domain.advogado.AdvogadoEntity;
import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.audiencia.dtos.AudienciaResponseDTO;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.enums.StatusCadastroTarefa;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import br.gov.agu.pace.domain.tarefa.TarefaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public class AudienciaMapper {

    public AudienciaEntity toEntity(
            AudienciaDTO dto,
            PautaEntity pauta,
            AssuntoEntity assunto,
            List<AdvogadoEntity> advogados
    ) {
        AudienciaEntity entity = new AudienciaEntity();
        entity.setNumeroProcesso(dto.getNumeroProcesso());
        entity.setNomeParte(dto.getPoloAtivo());
        entity.setHorario(dto.getHora());
        entity.setTipoContestacao(dto.getTipoContestacao());
        entity.setAssunto(assunto);
        entity.setPauta(pauta);
        entity.setAdvogados(new LinkedHashSet<>(advogados));
        entity.setAnaliseAvaliador(RespostaAnaliseAvaliador.ANALISE_PENDENTE);

        
        // Audiência é prioritária se houver pelo menos um advogado prioritário
        boolean hasPrioritario = advogados.stream().anyMatch(AdvogadoEntity::isPrioritario);
        entity.setPrioritaria(hasPrioritario);
        entity.setProcessoId(dto.getProcessoId());

        entity.setClasseJudicial(dto.getClasseJudicial());
        entity.setSubnucleo(dto.getSubnucleo());
        
        return entity;
    }

    public AudienciaResponseDTO toResponseDTO(AudienciaEntity entity, UserEntity usuarioSolicitante){
        AudienciaResponseDTO responseDTO = new AudienciaResponseDTO();
        responseDTO.setAudienciaId(entity.getAudienciaId());
        responseDTO.setNumeroProcesso(entity.getNumeroProcesso());
        responseDTO.setHorario(entity.getHorario());
        responseDTO.setData(entity.getPauta().getData());
        responseDTO.setNomeParte(entity.getNomeParte());
        responseDTO.setAdvogados(entity.getAdvogados());
        responseDTO.setAssunto(entity.getAssunto().getNome());
        responseDTO.setNovaAudiencia(entity.isNovaAudiencia());
        responseDTO.setTipoContestacao(entity.getTipoContestacao().getDescricao());
        responseDTO.setAnaliseAvaliador(entity.getAnaliseAvaliador().getDescricao());
        responseDTO.setObservacao(entity.getObservacao());
        responseDTO.setClasseJudicial(entity.getClasseJudicial());
        responseDTO.setSubnucleo(entity.getSubnucleo());
        
        // Busca a tarefa do usuário solicitante
        Long tarefaIdDoUsuario = entity.getTarefas().stream()
                .filter(tarefa -> tarefa.getDestinatario().getSapiensId().equals(usuarioSolicitante.getSapiensId()))
                .map(TarefaEntity::getTarefaId)
                .findFirst()
                .orElse(null);
        
        responseDTO.setTarefaId(tarefaIdDoUsuario);
        return responseDTO;
    }
}
