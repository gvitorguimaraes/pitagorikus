package br.dev.gvitorguimaraes.pitagorikus.controller;

import br.dev.gvitorguimaraes.pitagorikus.controller.dto.ResponseDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.dto.StudyGroupPostDTO;
import br.dev.gvitorguimaraes.pitagorikus.controller.mappers.StudyGroupPostMapper;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupPost;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroup;
import br.dev.gvitorguimaraes.pitagorikus.model.StudyGroupUser;
import br.dev.gvitorguimaraes.pitagorikus.model.User;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupPostService;
import br.dev.gvitorguimaraes.pitagorikus.service.IStudyGroupService;
import br.dev.gvitorguimaraes.pitagorikus.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

@RestController
@RequestMapping("/api/study-group/{groupId}/post")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Posts", description = "Endpoints para gerenciar posts em grupos de estudo")
public class PostController extends ControllerBase {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudyGroupService studyGroupService;

    @Autowired
    private IStudyGroupPostService postService;

    @Operation(
            summary = "Cria um novo post",
            description = "Cria um post dentro de um grupo de estudo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
            }
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<StudyGroupPostDTO>> createPost(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID alfanumérico do grupo", example = "1") @PathVariable String groupId,
            @RequestBody StudyGroupPostDTO dto) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup group = studyGroupService.getByGroupIdWithPermissionCheck(user, groupId)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            StudyGroupUser groupUser = group.getUsers().stream()
                                        .filter(gu -> gu.getId().equals(dto.userId()))
                                        .findFirst().orElseThrow(() ->
                                            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group User not found"));

            StudyGroupPost entity = StudyGroupPostMapper.toEntity(dto);

            StudyGroupPost saved = postService.create(groupUser, group, entity);

            return ResponseEntity.ok(getSuccessResponse(StudyGroupPostMapper.toDTO(saved)));
        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(getErrorResponse(HttpStatus.FORBIDDEN.value(), ade.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            summary = "Deleta post",
            description = "Remove permanentemente um post do grupo de estudo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post removido"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Post não encontrado")
            }
    )
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDTO<Void>> deletePost(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do post", example = "10") @PathVariable Long postId) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroupPost post = postService.getById(postId)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            postService.delete(post, user);
            return ResponseEntity.ok(null);
        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(getErrorResponse(HttpStatus.FORBIDDEN.value(), ade.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            summary = "Lista posts do grupo (feed infinito)",
            description = "Retorna posts do grupo em ordem decrescente de criação. Usa lastPostId para buscar mais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts retornados"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Sem permissão"),
                    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
            }
    )
    @GetMapping("/feed")
    public ResponseEntity<ResponseDTO<StudyGroupPostDTO>> getFeed(
            @AuthenticationPrincipal Jwt jwt,
            @Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId,
            @Parameter(description = "Último post recebido (para paginação infinita)", example = "100")
            @RequestParam(required = false) Long lastPostId,
            @Parameter(description = "Quantidade de posts a retornar", example = "20")
            @RequestParam(defaultValue = "20") int limit
    ) {
        try {
            User user = userService.recoveryUserFromUsername(jwt.getSubject())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            StudyGroup group = studyGroupService.getByIdWithPermissionCheck(user, groupId, false)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            List<StudyGroupPostDTO> posts = postService.getFeed(group, lastPostId, limit).stream()
                    .map(StudyGroupPostMapper::toDTO)
                    .toList();

            return ResponseEntity.ok(getSuccessResponse(posts));
        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(getErrorResponse(HttpStatus.FORBIDDEN.value(), ade.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
