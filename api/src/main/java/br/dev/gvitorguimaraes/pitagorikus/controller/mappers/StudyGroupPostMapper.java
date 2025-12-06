package br.dev.gvitorguimaraes.pitagorikus.controller.mappers;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupPostDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupPost;

public class StudyGroupPostMapper {

    public static StudyGroupPostDTO toDTO(StudyGroupPost entity) {
        if (entity == null) return null;

        return new StudyGroupPostDTO(
                entity.getId(),
                entity.getGroup() != null ? entity.getGroup().getId() : null,
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getTitle(),
                entity.getDescription(),
                entity.getDate(),
                entity.getActivityTimeInSeconds(),
                entity.getUrlPhoto()
        );
    }

    public static StudyGroupPost toEntity(StudyGroupPostDTO dto) {
        if (dto == null) return null;

        StudyGroupPost entity = new StudyGroupPost();
        entity.setId(dto.id());
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setDate(dto.date());
        entity.setActivityTimeInSeconds(dto.activityTimeInSeconds());
        entity.setUrlPhoto(dto.urlPhoto());

        return entity;
    }
}
