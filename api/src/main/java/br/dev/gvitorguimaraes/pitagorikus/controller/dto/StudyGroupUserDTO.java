package br.dev.gvitorguimaraes.pitagorikus.controller.dto;

public record StudyGroupUserDTO(Long userId, Boolean isGroupAdmin, Integer score, StudyGroupDTO group) {
}
