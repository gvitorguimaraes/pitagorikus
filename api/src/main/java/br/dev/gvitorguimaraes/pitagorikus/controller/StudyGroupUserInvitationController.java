package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupUserInvitationDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupUserInvitationService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/study-group-user-invitation/")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Grupos de Estudo", description = "Endpoints relacionados a grupos de estudo")
public class StudyGroupUserInvitationController extends ControllerBase {

    @Autowired
    private IStudyGroupUserInvitationService service;

    @Autowired
    private IUserService userService;

    @Operation(
            summary = "Busca os convites pendentes",
            description = "Recupera todos os convites pendentes de aceite para o usuário autenticado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Convites recuperados com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<StudyGroupUserInvitationDTO>> getAllPendingInvites(@AuthenticationPrincipal Jwt jwt) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            List<StudyGroupUserInvitationDTO> invitationsDTO = new ArrayList<>();
            for (StudyGroupUserInvitation invitation : service.getActiveInvites(user)) {
                invitationsDTO.add(
                                    new StudyGroupUserInvitationDTO(
                                      invitation.getId(),
                                      invitation.getGroup().getName(),
                                      invitation.getGroup().getGroupId(),
                                      invitation.getInviter().getUsername(),
                                      invitation.getAccepted(),
                                      invitation.getAcceptanceDate()
                                    )
                );
            }
            return ResponseEntity.ok(getSuccessResponse(invitationsDTO));
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
            summary = "Aceita um convite",
            description = "Aceita um convite",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Convites aceito com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "500", description = "Erro interno")
            }
    )
    @PutMapping("/accept/{id}")
    public ResponseEntity<ResponseDTO<?>> acceptGroupInvite(@AuthenticationPrincipal Jwt jwt,
                                                                       @PathVariable Long id) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
            service.acceptInvite(user, id);
            return ResponseEntity.ok().build();
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
