package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de registro de um novo usuário")
public record RegisterDTO(String name, String email, String username, String password)
{
}
