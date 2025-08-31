package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupUserInvitationDTO;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUserInvitation;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupUserInvitationService;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;
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
public class StudyGroupUserInvitationController extends ControllerBase {

    @Autowired
    private IStudyGroupUserInvitationService service;

    @Autowired
    private IUserService userService;

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
