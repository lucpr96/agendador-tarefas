package com.agendador.agendador_tarefas.business;

import com.agendador.agendador_tarefas.business.dto.TarefasDTO;
import com.agendador.agendador_tarefas.business.mapper.TarefaUpdateConverter;
import com.agendador.agendador_tarefas.business.mapper.TarefasConverter;
import com.agendador.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.agendador.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.agendador.agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefasUpdateConverter;


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


    // Metodo de Deletar tarefa vi Id
    public void deletaTarefasPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id: id inexistente " + id,
                    e.getCause());
        }
    }


    // Metodo para alterar Status da Tarefas
    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }



    // Metodo de Update
    public TarefasDTO updateDasTarefas(TarefasDTO dto, String id) {
        try{
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefasUpdateConverter.updateTarefas(dto, entity);
            return tarefasConverter.paraTarefasDTO(tarefasRepository.save(entity));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }




}
