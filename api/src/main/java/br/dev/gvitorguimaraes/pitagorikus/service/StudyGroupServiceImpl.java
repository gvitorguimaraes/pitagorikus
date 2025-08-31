package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.repo.StudyGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudyGroupServiceImpl implements IStudyGroupService{

	@Autowired
	private StudyGroupRepo repo;
	
	@Override
	public StudyGroup create(User user, StudyGroup studyGroup) throws Exception {
		if (studyGroup == null || user == null) throw new IllegalArgumentException("User or group are null");
		StudyGroupUser groupUser = new StudyGroupUser(user, studyGroup);
		groupUser.setGroupAdmin(true);
		studyGroup.getUsers().add(groupUser);
		
		studyGroup.setGroupId(generateUniqueIdentifier());
		
		return repo.save(studyGroup);
	}
	
	private String generateUniqueIdentifier() {
        String groupId;
        do {
        	groupId = randomIdentifier();
        } while (repo.existsByGroupId(groupId));
        return groupId;
    }

    private String randomIdentifier() {
    	SecureRandom random = new SecureRandom();
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

	@Override
	public StudyGroup update(StudyGroup studyGroup) throws Exception {
        if(studyGroup != null) {
            return repo.save(studyGroup);
        }
        return null;
	}

	@Override
	public void delete(StudyGroup studyGroup) throws Exception {
        if (studyGroup != null) {
            studyGroup.setExclusion(LocalDateTime.now());
            repo.save(studyGroup);
        }
	}

	@Override
	public Optional<StudyGroup> getById(Long id) throws Exception {
		return repo.findById(id);
	}

    @Override
    public Optional<StudyGroup> getByIdWithPermissionCheck(User user, Long id, boolean onlyAdmin) throws Exception {
        StudyGroup group = repo.findById(id).orElse(null);
        if (group == null) return Optional.empty();

        boolean userHasPermission = false;
        if (onlyAdmin) {
            userHasPermission = group.getUsers().stream()
                                        .anyMatch(u -> u.getUser().equals(user) && u.isGroupAdmin());
        } else {
            userHasPermission = group.getUsers().stream()
                                        .anyMatch(u -> u.getUser().equals(user));
        }
        if (!userHasPermission) {
            throw new AccessDeniedException("User has no permission to access this group");
        }
        return repo.findById(id);
    }

}
