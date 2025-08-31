package br.dev.gvitorguimaraes.pitagorikus.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private final IUserService userService;
	
	public UserDetailsServiceImpl(IUserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.recoveryUserFromUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userService.registerLogin(user);
		return Optional.of(user)
					   .map(UserAuthenticated::new)
					   .orElseThrow(() -> new IllegalStateException("Failed to convert User in UserDetails"));
	}
}
