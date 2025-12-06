package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um grupo de estudo")
public record StudyGroupDTO(

        @Schema(description = "Identificador interno do servidor (preenchimento dispensável)", example = "1")
        Long id,

        @Schema(description = "Identificador alfanumérico único do grupo", example = "AB2C1D0")
        String groupId,

        @Schema(description = "Nome do grupo de estudo", example = "Grupo de Matemática")
        String name,

        @Schema(description = "Descrição do grupo", example = "Grupo para estudos de álgebra linear")
        String description,

        @Schema(description = "URL da foto do grupo", example = "https://cdn.exemplo.com/foto.png")
        String urlPhoto
){}
