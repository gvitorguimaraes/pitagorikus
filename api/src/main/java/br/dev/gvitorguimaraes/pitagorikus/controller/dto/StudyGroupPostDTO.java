package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Post dentro de um grupo de estudo")
public record StudyGroupPostDTO(

        @Schema(description = "ID do post", example = "101")
        Long id,

        @Schema(description = "ID do grupo", example = "5")
        Long groupId,

        @Schema(description = "ID do integrante (StudyGroupUser) que criou o post", example = "12")
        Long userId,

        @Schema(description = "Título do post", example = "Resumo da aula de matemática")
        String title,

        @Schema(description = "Descrição detalhada do post", example = "Resumo dos principais tópicos de derivadas")
        String description,

        @Schema(description = "Data de criação do post", example = "2025-09-14")
        LocalDate date,

        @Schema(description = "Tempo de atividade em segundos relacionado ao post", example = "3600")
        Long activityTimeInSeconds,

        @Schema(description = "URL da imagem do post", example = "https://cdn.site.com/images/post123.png")
        String urlPhoto

) {}
