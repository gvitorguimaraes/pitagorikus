package br.dev.gvitorguimaraes.pitagorikus.security;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${jwt.public.key}")
	private Resource keyResource;
	
	@Value("${jwt.private.key}")
	private Resource privResource;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception{
		httpSec.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(
					auth -> 
							auth.requestMatchers("/auth").permitAll()
							    .requestMatchers("/api/user/register").permitAll()
							    .anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.oauth2ResourceServer(
					conf -> conf.jwt(Customizer.withDefaults()));
		
		return httpSec.build();
	}
	
	private RSAPublicKey getKey() throws Exception {
	    try (var in = keyResource.getInputStream()) {
	        String pem = new String(in.readAllBytes(), StandardCharsets.UTF_8);
	        return PemUtils.parsePublicKey(pem);
	    }
	}

	private RSAPrivateKey getPriv() throws Exception {
	    try (var in = privResource.getInputStream()) {
	        String pem = new String(in.readAllBytes(), StandardCharsets.UTF_8);
	        return PemUtils.parsePrivateKey(pem);
	    }
	}
	
	@Bean
	JwtDecoder jwtDecoder() throws Exception{
		return NimbusJwtDecoder.withPublicKey(getKey()).build();
	}
	
	@Bean
	JwtEncoder jwtEncoder() throws Exception {
		var jwk = new RSAKey.Builder(getKey()).privateKey(getPriv()).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
