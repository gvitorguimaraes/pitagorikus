package br.dev.gvitorguimaraes.pitagorikus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Serviços de autenticação")
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class AuthController extends ControllerBase
{
    @Autowired
    private IAuthService service;

    @Operation(
            summary = "Autenticar",
            description = "Autentica o usuário via **Basic Auth** e retorna um token JWT para ser usado nas próximas requisições",
            security = @SecurityRequirement(name = "basicAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticado com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
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
