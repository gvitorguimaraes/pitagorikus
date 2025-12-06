package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.StudyGroupMapper;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupService;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupUserInvitationService;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/study-group")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Grupos de Estudo", description = "Endpoints relacionados a grupos de estudo")
public class StudyGroupController extends ControllerBase {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IStudyGroupService service;

    @Autowired
    private IStudyGroupUserInvitationService inviteUserService;

    @Operation(
            summary = "Cria grupo de estudo",
            description = "Cria um novo grupo de estudo vinculado ao usuário autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grupo criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> createStudyGroup(
                @AuthenticationPrincipal Jwt jwt,
                @RequestBody StudyGroupDTO dto) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                               .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup studyGroup = new StudyGroup();
            studyGroup.setName(dto.name());
            studyGroup.setDescription(dto.description());
            studyGroup = service.create(user, studyGroup);

            return ResponseEntity.ok(getSuccessResponse(StudyGroupMapper.toDTO(studyGroup)));
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

    @Operation(
            summary = "Atualiza grupo de estudo",
            description = "Atualiza dados de um grupo de estudo existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> updateStudyGroup(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do grupo de estudo", example = "1") @PathVariable Long id,
            @RequestBody StudyGroupDTO dto) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup studyGroup = service.getByIdWithPermissionCheck(user, id, true)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            studyGroup.setName(dto.name());
            studyGroup.setDescription(dto.description());
            studyGroup.setUrlPhoto(dto.urlPhoto());
            studyGroup = service.update(studyGroup);

            return ResponseEntity.ok(getSuccessResponse(StudyGroupMapper.toDTO(studyGroup)));
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

    @Operation(
            summary = "Busca grupo de estudo por ID",
            description = "Retorna dados de um grupo de estudo acessível ao usuário autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grupo encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> getById(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do grupo de estudo", example = "1") @PathVariable Long id) {
        try {
    	   	User user = userService.recoveryUserFromUsername(jwt.getSubject())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup studyGroup = service.getByIdWithPermissionCheck(user, id, false)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            return ResponseEntity.ok(getSuccessResponse(StudyGroupMapper.toDTO(studyGroup)));
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

    @Operation(
            summary = "Exclui grupo de estudo",
            description = "Remove permanentemente um grupo de estudo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Grupo removido"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteStudyGroup(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do grupo de estudo", example = "1") @PathVariable Long id) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup studyGroup = service.getByIdWithPermissionCheck(user, id, true)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            service.delete(studyGroup);

            return ResponseEntity.ok(null);
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

    @Operation(
            summary = "Convida usuário para grupo de estudo",
            description = "Convida outro usuário para participar do grupo de estudo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário convidado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado")
            }
    )
    @PutMapping("/{id}/invite/{username}")
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> inviteUser(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do grupo de estudo", example = "1") @PathVariable Long id,
            @Parameter(description = "Username do convidado", example = "johndoe") @PathVariable String username) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup studyGroup = service.getByIdWithPermissionCheck(user, id, true)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            User userInvited = userService.recoveryUserFromUsername(username)
                                            .orElseThrow(() -> new IllegalArgumentException("Guest user not found"));

            inviteUserService.inviteUserToStudyGroup(studyGroup, userInvited, user);

            return ResponseEntity.ok(getSuccessResponse(null));
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
