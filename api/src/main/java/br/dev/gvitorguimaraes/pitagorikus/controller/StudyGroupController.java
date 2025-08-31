package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.StudyGroupMapper;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupService;
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

@RestController
@RequestMapping("/api/study-group")
public class StudyGroupController extends ControllerBase {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IStudyGroupService service;

    @Autowired
    private IStudyGroupUserInvitationService inviteUserService;
	
	@PostMapping
	public ResponseEntity<ResponseDTO<StudyGroupDTO>> createStudyGroup(@AuthenticationPrincipal Jwt jwt, 
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

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> updateStudyGroup(@AuthenticationPrincipal Jwt jwt,
                                                                       @PathVariable Long id,
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

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<StudyGroupDTO>> getById(@AuthenticationPrincipal Jwt jwt,
                                                              @PathVariable Long id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteStudyGroup(@AuthenticationPrincipal Jwt jwt,
                                                                       @PathVariable Long id) {
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

    @PutMapping("/{id}/invite/{username}")
    public ResponseEntity<ResponseDTO<StudyGroupDTO>> inviteUser(@AuthenticationPrincipal Jwt jwt,
                                                                       @PathVariable Long id,
                                                                       @PathVariable String username) {
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
