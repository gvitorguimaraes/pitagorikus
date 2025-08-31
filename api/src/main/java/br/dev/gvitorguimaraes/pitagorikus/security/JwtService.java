package br.dev.gvitorguimaraes.pitagorikus.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
	@Autowired
	private JwtEncoder jwtEncoder;
	
	public String generateToken(Authentication authentication) {
		Long expiration = 3600L;
		
		String scopes = authentication.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.joining(" "));
		
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
								.issuer("pitagorikus-app")
								.issuedAt(now)
								.expiresAt(now.plusSeconds(expiration))
								.subject(authentication.getName())
								.claim("scope", scopes)
								.build();
				
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
}
