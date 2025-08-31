package br.dev.gvitorguimaraes.pitagorikus.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.gvitorguimaraes.pitagorikus.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	public Optional<User> findByUsername(String username);
	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
}
