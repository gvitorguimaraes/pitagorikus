package br.dev.gvitorguimaraes.pitagorikus.service;

import org.springframework.security.core.Authentication;

public interface IAuthService {
	public String login(Authentication authentication);
}
