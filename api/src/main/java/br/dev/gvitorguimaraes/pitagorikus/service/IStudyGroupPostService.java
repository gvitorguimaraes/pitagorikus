package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupPost;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;

import java.util.List;
import java.util.Optional;

public interface IStudyGroupPostService {
    StudyGroupPost create(StudyGroupUser author, StudyGroup group, StudyGroupPost post);
    void delete(StudyGroupPost post, User requester);
    Optional<StudyGroupPost> getById(Long id);
    List<StudyGroupPost> getFeed(StudyGroup group, Long lastPostId, int limit);
}
