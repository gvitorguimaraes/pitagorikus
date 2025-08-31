package br.dev.gvitorguimaraes.pitagorikus.service;

import java.util.Optional;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.User;

public interface IStudyGroupService {
	StudyGroup create(User user, StudyGroup studyGroup) throws Exception;
	StudyGroup update(StudyGroup studyGroup) throws Exception;
	void delete(StudyGroup studyGroup) throws Exception;
	Optional<StudyGroup> getById(Long id) throws Exception;
    Optional<StudyGroup> getByIdWithPermissionCheck(User user, Long id, boolean onlyAdmin) throws Exception;
}
