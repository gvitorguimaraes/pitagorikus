package br.dev.gvitorguimaraes.pitagorikus.repo;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyGroupRepo extends JpaRepository<StudyGroup, Long>{
	public boolean existsByGroupId(String groupId);
    public Optional<StudyGroup> findByGroupId(String groupId);
}
