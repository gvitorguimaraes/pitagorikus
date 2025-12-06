package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token JWT gerado no login")
public record JwtTokenDTO(
        @Schema(description = "Token JWT válido para autenticação", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token
){ }
