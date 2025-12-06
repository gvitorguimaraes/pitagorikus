package br.dev.gvitorguimaraes.pitagorikus.repo;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupPostRepo extends JpaRepository<StudyGroupPost, Long> {
    List<StudyGroupPost> findTopByGroupOrderByInclusionDesc(StudyGroup group, Pageable pageable);
    List<StudyGroupPost> findByGroupAndIdLessThanOrderByInclusionDesc(StudyGroup group, Long lastPostId, Pageable pageable);
}

