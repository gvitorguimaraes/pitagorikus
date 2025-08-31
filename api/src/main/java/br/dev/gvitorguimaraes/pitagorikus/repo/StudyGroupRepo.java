package br.dev.gvitorguimaraes.pitagorikus.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;

public interface StudyGroupRepo extends JpaRepository<StudyGroup, Long>{
	public boolean existsByGroupId(String groupId);
}
