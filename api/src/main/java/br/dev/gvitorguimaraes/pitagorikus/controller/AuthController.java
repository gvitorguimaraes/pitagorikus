package br.dev.gvitorguimaraes.pitagorikus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.JwtTokenDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.service.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends ControllerBase
{
    @Autowired
    private IAuthService service;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<JwtTokenDTO>> login(Authentication authentication) {
    	try {
    		String token = service.login(authentication);
            if (token != null && !token.isEmpty()) {
                return ResponseEntity.ok().body(getSuccessResponse(new JwtTokenDTO(token)));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            					 .body(getErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Não foi possível realizar login"));
    	} 
 
	catch (UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				 .body(getErrorResponse(HttpStatus.BAD_REQUEST.value(), "Usuário ou senha incorretos"));
    }
	catch (BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				 .body(getErrorResponse(HttpStatus.BAD_REQUEST.value(), "Usuário ou senha incorretos"));
	}
    	catch (Exception e) {
        	return ResponseEntity.internalServerError().build();
        }
    }

}
