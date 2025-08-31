package br.dev.gvitorguimaraes.pitagorikus.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.dev.gvitorguimaraes.pitagorikus.model.User;
import io.jsonwebtoken.lang.Collections;

public class UserAuthenticated implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private final User user;
	
	public UserAuthenticated(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (user.getRole().isAdmin()){
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		}
		else if (user.getRole().isUser()){
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
