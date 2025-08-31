package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;

import java.util.List;

public interface IStudyGroupUserService {
    List<StudyGroupUser> getAllStudyGroups(User user);
}
