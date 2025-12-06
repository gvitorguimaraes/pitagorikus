package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Objeto padrão de resposta da API")
public record ResponseDTO<T>(
        Integer apiVersion,
        List<T> obj,
        ErroResponseDTO error
) { }
