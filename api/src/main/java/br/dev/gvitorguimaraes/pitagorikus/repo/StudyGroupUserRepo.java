package br.dev.gvitorguimaraes.pitagorikus.repo;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupUserRepo extends JpaRepository<StudyGroupUser, Long> {
    List<StudyGroupUser> findAllByUserAndExclusionIsNull(User user);
}
