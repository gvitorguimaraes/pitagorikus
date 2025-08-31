package br.dev.gvitorguimaraes.pitagorikus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.dev.gvitorguimaraes.pitagorikus.security.JwtService;

@Service
public class AuthServiceImpl implements IAuthService{

	@Autowired
	private JwtService jwtService;
	
	@Override
	public String login(Authentication authentication) {
		return jwtService.generateToken(authentication);
	}

}
