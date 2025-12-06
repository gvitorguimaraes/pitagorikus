package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupUserDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.StudyGroupMapper;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.StudyGroupUserMapper;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupUserInvitationService;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupUserService;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study-group-user/")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Grupos de Estudo", description = "Endpoints relacionados a grupos de estudo")
public class StudyGroupUserController extends ControllerBase{

    @Autowired
    private IStudyGroupUserService service;

    @Autowired
    private IUserService userService;

    @Operation(
            summary = "Buscar grupos de estudo do usuário",
            description = "Recupera todos os grupos de estudo ativos do usuário autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grupos recueperados com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<StudyGroupUserDTO>> getAllGroups(@AuthenticationPrincipal Jwt jwt) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            List<StudyGroupUser> userGroups = service.getAllStudyGroups(user);
            return ResponseEntity.ok(getSuccessResponse(userGroups.stream()
                                                            .map(StudyGroupUserMapper::toDTOWithGroup)
                                                            .collect(Collectors.toList())
                                                        )
                                    );
        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(getErrorResponse(HttpStatus.FORBIDDEN.value(), ade.getMessage()));
        } catch (IllegalArgumentException ie) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse(HttpStatus.BAD_REQUEST.value(), ie.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

}
