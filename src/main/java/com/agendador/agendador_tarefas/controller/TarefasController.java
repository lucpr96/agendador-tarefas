package com.agendador.agendador_tarefas.controller;

import com.agendador.agendador_tarefas.business.TarefasService;
import com.agendador.agendador_tarefas.business.dto.TarefasDTO;
import com.agendador.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    //
    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    //
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal) {

        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));

    }

    //
    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaListaTarefasPorEmail(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }


    //
    @DeleteMapping
    public ResponseEntity<Void> deletaTarefasViaId(@RequestParam("id") String id) {
        tarefasService.deletaTarefasPorId(id);
        return ResponseEntity.ok().build();
    }


    //
    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(@RequestParam("status")StatusNotificacaoEnum status, @RequestParam("id") String id) {
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id));
    }


    //
    @PutMapping
    public ResponseEntity<TarefasDTO> updateDeTodasTarefas(@RequestBody TarefasDTO dto, @RequestParam("id") String id) {
        return ResponseEntity.ok(tarefasService.updateDasTarefas(dto, id));
    }



}