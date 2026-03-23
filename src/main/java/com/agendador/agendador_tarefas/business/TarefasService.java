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
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;


    // Metodo de gravar uma Tarefa
    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extractUserEmail(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefasEntity(dto);

        return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
    }


    // Metodo de buscar tarefas realizadas em determinado periodo de tempo
    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }


    // Metodo de buscar tarefas por email do usuario:
    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extractUserEmail(token.substring(7));
        return tarefasConverter.paraListTarefasDTO(tarefasRepository.findByEmailUsuario(email));

    }
}
