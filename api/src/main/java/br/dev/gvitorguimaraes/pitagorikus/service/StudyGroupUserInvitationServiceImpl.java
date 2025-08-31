package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupUserInvitationDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.repo.StudyGroupUserInvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudyGroupUserInvitationServiceImpl implements IStudyGroupUserInvitationService{

    @Autowired
    private StudyGroupUserInvitationRepo repo;

    @Override
    public StudyGroupUserInvitation save(StudyGroupUserInvitation invitation) {
        return repo.save(invitation);
    }

    @Override
    public void inviteUserToStudyGroup(StudyGroup studyGroup, User userInvited, User inviter) throws Exception {
        if (studyGroup == null || userInvited == null || inviter == null){
            throw new IllegalArgumentException("Incomplete data to create an invitation, verify the params.");
        }

        StudyGroupUserInvitation invitation = new StudyGroupUserInvitation(studyGroup,
                                                                            userInvited,
                                                                            inviter);
        save(invitation);
    }

    @Override
    public List<StudyGroupUserInvitation> getActiveInvites(User userInvited) {
        return repo.findAllByUserInvitedAndAcceptedIsNullAndExclusionIsNull(userInvited);
    }

    @Override
    public void acceptInvite(User user, Long inviteId) throws Exception {
        StudyGroupUserInvitation invitation = repo.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invite id:"+inviteId+" not found"));
        if (user.equals(invitation.getUserInvited())) {
            invitation.setAccepted(true);
            invitation.setAcceptanceDate(LocalDateTime.now());
            repo.save(invitation);
        } else {
            throw new AccessDeniedException("User has no permission to accept this group invite");
        }
    }
}
