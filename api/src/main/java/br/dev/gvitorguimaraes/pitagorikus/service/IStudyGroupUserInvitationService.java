package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;

import java.util.List;
import java.util.Optional;

public interface IStudyGroupUserInvitationService {
    StudyGroupUserInvitation save(StudyGroupUserInvitation invitation);
    void inviteUserToStudyGroup(StudyGroup studyGroup, User userInvited, User inviter) throws Exception;
    List<StudyGroupUserInvitation> getActiveInvites(User user);
    void acceptInvite(User user, Long inviteId) throws Exception;
}
