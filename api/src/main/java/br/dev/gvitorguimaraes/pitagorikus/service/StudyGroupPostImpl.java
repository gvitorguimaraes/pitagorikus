package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupPost;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.repo.StudyGroupPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupPostImpl implements  IStudyGroupPostService{

    @Autowired
    private StudyGroupPostRepo repository;

    @Override
    public StudyGroupPost create(StudyGroupUser author, StudyGroup group, StudyGroupPost post) {
        post.setUser(author);
        post.setGroup(group);
        post.setDate(LocalDate.now());
        return repository.save(post);
    }

    @Override
    public void delete(StudyGroupPost post, User requester) {
        if (!post.getUser().getUser().equals(requester) && !requester.getRole().isAdmin()) {
            throw new AccessDeniedException("Sem permissão para excluir este post");
        }
        repository.delete(post);
    }

    @Override
    public Optional<StudyGroupPost> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StudyGroupPost> getFeed(StudyGroup group, Long lastPostId, int limit) {
        if (lastPostId == null) {
            return repository.findTopByGroupOrderByInclusionDesc(group, PageRequest.of(0, limit));
        }
        return repository.findByGroupAndIdLessThanOrderByInclusionDesc(group, lastPostId, PageRequest.of(0, limit));
    }
}
