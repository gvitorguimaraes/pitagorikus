package br.dev.gvitorguimaraes.pitagorikus.controller.mappers;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupUserDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;

public class StudyGroupUserMapper {
	
	public static StudyGroupUserDTO toDTO(StudyGroupUser entity) {
        if (entity == null) return null;
        return new StudyGroupUserDTO(
                entity.getUser().getId(),
                entity.getGroupAdmin(),
                entity.getScore(),
                null
        );
	}

    public static StudyGroupUserDTO toDTOWithGroup(StudyGroupUser entity){
        if (entity == null) return null;

        StudyGroupDTO groupDTO = null;
        if (entity.getGroup() != null) {
            groupDTO = new StudyGroupDTO(entity.getGroup().getId(),
                                            entity.getGroup().getGroupId(),
                                            entity.getGroup().getName(),
                                            entity.getGroup().getDescription(),
                                            entity.getGroup().getUrlPhoto()
                                        );
        }
        return new StudyGroupUserDTO(entity.getUser().getId(),
                                        entity.getGroupAdmin(),
                                        entity.getScore(),
                                        groupDTO
                                    );
    }
	
	public static StudyGroup toEntity(StudyGroupDTO dto) {
		return null;
	}
}
