package br.dev.gvitorguimaraes.pitagorikus.controller.mappers;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;

public class StudyGroupMapper {
	
	public static StudyGroupDTO toDTO(StudyGroup entity) {
		if (entity == null) return null;

        StudyGroupDTO dto = new StudyGroupDTO(entity.getId(),
                                                entity.getGroupId(),
                                                entity.getName(),
                                                entity.getDescription(),
                                                entity.getUrlPhoto()
                                             );

        // add users

		return dto;
	}
	
	public static StudyGroup toEntity(StudyGroupDTO dto) {
		return null;
	}
}
