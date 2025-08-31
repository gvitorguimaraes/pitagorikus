package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

import java.time.LocalDateTime;

public record StudyGroupUserInvitationDTO(Long id,
                                          String groupName,
                                          String groupId,
                                          String userInviterName,
                                          Boolean accepted,
                                          LocalDateTime acceptanceDate) {
}
