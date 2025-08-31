package br.dev.gvitorguimaraes.pitagorikus.service;

import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.model.enums.UserRoleEnum;
import br.dev.gvitorguimaraes.pitagorikus.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserRepo repo;
	
	
	@Override
	@Transactional
	public User createNew(User user) throws Exception {
		if (repo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email informado já está em uso!");
		}
		else if (repo.existsByUsername(user.getUsername())){
			throw new IllegalArgumentException("Username informado já está em uso!");
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(UserRoleEnum.USER);
        user.setActive(true);
		return repo.save(user);
	}
	
	@Override
	public Optional<User> recoveryUserFromUsername(String username) {
		return repo.findByUsername(username);
	}
	
	@Override
	public void registerLogin(User user) {
		if (user != null) {
			user.setLastLoginData(LocalDateTime.now());
			repo.save(user);
		}
	}
	
	@Override
	public User save(User user) {
		return repo.save(user);
	}
}
