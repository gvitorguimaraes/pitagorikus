package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados do convite")
public record StudyGroupUserInvitationDTO(Long id,
                                          String groupName,
                                          String groupId,
                                          String userInviterName,
                                          Boolean accepted,
                                          LocalDateTime acceptanceDate) {
}
