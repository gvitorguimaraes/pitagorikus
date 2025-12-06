package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do integrante do um grupo de estudos")
public record StudyGroupUserDTO(

        @Schema(description = "Identificador do usuário", example = "1")
        Long userId,
        @Schema(description = "Se o usuário é administrador do grupo", example = "true")
        Boolean isGroupAdmin,
        @Schema(description = "Score do usuário no grupo", example = "25.5")
        Integer score,
        @Schema(description = "Grupo", example = "StudyGroupDTO")
        StudyGroupDTO group

) { }
