package com.agendador.agendador_tarefas.infrastructure.client;

import com.agendador.agendador_tarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
    UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                    @RequestHeader("Authorization") String token);
}

// Traz o metodo GET de busca por email para que seja possivel fazer a busca do token do usuario via email
// Interface não precisa da declaracao de metodo public ou private
// Ao deixar apenas o UsuarioDTO significa deixar apenas o retorno do metodo que sera criado dentro da pasta cliente
// Para fazer a busca por email é necessário inserir a Requisição de Parametros e Requisição Header com Authorization para o token
//
