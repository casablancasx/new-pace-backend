package br.gov.agu.pace.domain.audiencia.mapper;

import br.gov.agu.pace.domain.advogado.AdvogadoEntity;
import br.gov.agu.pace.domain.assunto.AssuntoEntity;
import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.StatusCadastroTarefa;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import org.springframework.stereotype.Component;

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
        entity.setAdvogados(advogados);
        entity.setStatusCadastroTarefaAvaliador(StatusCadastroTarefa.PENDENTE);
        entity.setStatusCadastroTarefaPautista(StatusCadastroTarefa.PENDENTE);
        
        // Audiência é prioritária se houver pelo menos um advogado prioritário
        boolean hasPrioritario = advogados.stream().anyMatch(AdvogadoEntity::isPrioritario);
        entity.setPrioritario(hasPrioritario);
        
        return entity;
    }
}
