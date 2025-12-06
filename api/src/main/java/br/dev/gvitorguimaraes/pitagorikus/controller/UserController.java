package br.dev.gvitorguimaraes.pitagorikus.controller;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.RegisterDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.UserProfileDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.UserProfileMapper;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.model.enums.InterestsProfileEnum;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Perfil", description = "Serviços de cadastro e perfil de usuários")
public class UserController extends ControllerBase {

	@Autowired
	private IUserService service;


    @Operation(
            summary = "Registrar",
            description = "Registrar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                     @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO<?>> createNewUser(@RequestBody RegisterDTO registerDTO) {
      try {
          	service.createNew(new User(registerDTO.name(), 
          						   registerDTO.email(), 
          						   registerDTO.username(), 
          						   registerDTO.password()
          						   ));
          	return ResponseEntity.ok().build();
      } catch (IllegalArgumentException ie) {
      		return ResponseEntity.badRequest()
      						 .body(getErrorResponse(HttpStatus.BAD_REQUEST.value(), ie.getMessage()));
      } catch (Exception e) {
          	return ResponseEntity.internalServerError()
          					 .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
      }
	}


    @Operation(
            summary = "Buscar dados",
            description = "Buscar dados do perfil",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados recuperados com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
	@GetMapping("/profile")
	public ResponseEntity<ResponseDTO<UserProfileDTO>> getProfile(@AuthenticationPrincipal Jwt jwt) {
		User user = service.recoveryUserFromUsername(jwt.getSubject()).orElse(null);
		if(user == null) {
			return ResponseEntity.badRequest().body(this.getErrorResponse(HttpStatus.NOT_FOUND.value(), "An error occurred while retrieving the authenticated user."));
		}
		return ResponseEntity.ok(getSuccessResponse(UserProfileMapper.toDTO(user.getProfile())));
	}


    @Operation(
            summary = "Atualizar dados",
            description = "Atualizar dados do perfil",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
	@PutMapping("/profile")
	public ResponseEntity<ResponseDTO<UserProfileDTO>> updateProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody UserProfileDTO dto) {
		try{
			User user = service.recoveryUserFromUsername(jwt.getSubject()).orElse(null);
			if(user == null) {
				return ResponseEntity.badRequest().body(getErrorResponse(HttpStatus.NOT_FOUND.value(), "An error occurred while retrieving the authenticated user."));
			}
			UserProfileMapper.updateEntity(user.getProfile(), dto);
			user = service.save(user);
			return ResponseEntity.ok(getSuccessResponse(UserProfileMapper.toDTO(user.getProfile())));
		}catch (Exception e) {
			return ResponseEntity.internalServerError()
 					 .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}


    @Operation(
            summary = "Recuperar interesses",
            description = "Recupera a lista de interesses disponíveis no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recuperados com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
	@GetMapping("/profile/interests")
	public ResponseEntity<ResponseDTO<Map<String, String>>> getInterestsList() {
		Map<String, String> map = new HashMap<>();
		for(InterestsProfileEnum interest : InterestsProfileEnum.values()) {
			map.put(interest.getCode(), interest.getDescription());
		}
		return ResponseEntity.ok(getSuccessResponse(map));
	}
}