package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.User;

import java.util.Optional;

public interface IUserService {
	public User createNew(User user) throws Exception;
	public Optional<User> recoveryUserFromUsername(String username);
	public void registerLogin(User user);
	public User save(User user);
}
