package com.agendador.agendador_tarefas.business;

import com.agendador.agendador_tarefas.business.dto.TarefasDTO;
import com.agendador.agendador_tarefas.business.mapper.TarefasConverter;
import com.agendador.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.agendador.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.agendador.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.agendador.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefaRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extractUserEmail(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefasEntity(dto);

        return tarefaConverter.paraTarefasDTO(tarefaRepository.save(entity));
    }
}
