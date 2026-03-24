package com.agendador.agendador_tarefas.business.mapper;

import com.agendador.agendador_tarefas.business.dto.TarefasDTO;
import com.agendador.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    @Mapping(source = "id", target = "id" )
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")

    // Convercao Mapper para Tarefas
    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);


    // Convercao Mapper para List<Tarefas>
    List<TarefasEntity> paraListTarefasEntity(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListTarefasDTO(List<TarefasEntity> entities);

}
