package com.agendador.agendador_tarefas.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTO {
    private String email;
    private String senha;

}

// Esse e o retorno do metodo usado na classe Usuarioclient
// Ao puxar a classe de UsuarioDTO foi possivel deixaar apenas o email e senha que são os responsaveis
// para gerar o token