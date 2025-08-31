package br.dev.gvitorguimaraes.pitagorikus.repo;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupUserInvitationRepo extends JpaRepository<StudyGroupUserInvitation, Long> {
    List<StudyGroupUserInvitation> findAllByUserInvitedAndAcceptedIsNullAndExclusionIsNull(User userInvited);
}
