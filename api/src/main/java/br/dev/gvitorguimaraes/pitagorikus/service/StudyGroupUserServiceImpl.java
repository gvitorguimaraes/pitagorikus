package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.repo.StudyGroupUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyGroupUserServiceImpl implements IStudyGroupUserService {

    @Autowired
    private StudyGroupUserRepo repo;

    @Override
    public List<StudyGroupUser> getAllStudyGroups(User user) {
        return repo.findAllByUserAndExclusionIsNull(user);
    }
}
