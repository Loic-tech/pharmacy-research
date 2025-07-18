package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.UserService;
import com.search.pharmacy.ws.model.*;
import java.util.List;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDTO> partialUserCreation(@RequestBody @Valid PartialUserDTO partialUserDTO) {
    log.debug("[ENDPOINT] request to create partial user");
    return ResponseEntity.ok(userService.partialUserCreation(partialUserDTO));
  }

  @PatchMapping("complete/{userId}")
  public ResponseEntity<UserDTO> completeUserCreation(
      @PathVariable("userId") Long userId,
      @ModelAttribute @Valid CompleteUserDTO completeUserDTO,
      @RequestPart List<MultipartFile> files) {
    log.debug("[ENDPOINT] request to create complete user");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.completeUserCreation(userId, completeUserDTO, files));
  }

  @PatchMapping("/{userId}/validate")
  public ResponseEntity<String> validateUser(@PathVariable("userId") Long userId) throws MessagingException {
    return ResponseEntity.ok(userService.validateUserProfile(userId));
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthenticatedUserDTO> login(@RequestBody @Valid LoginUserDTO loginUserDTO) {
    log.debug("[ENDPOINT] request to login user");
    return ResponseEntity.ok(userService.login(loginUserDTO));
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<UserDTO>> getUsers() {
    log.debug("[ENDPOINT] request to get all users");
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") Long id) {
    log.debug("[ENDPOINT] request to get user with id {}", id);
    return ResponseEntity.ok(userService.getUser(id));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId) {
    log.info("[ENDPOINT] Received request to delete user with id {}", userId);
    userService.delete(userId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable Long id, @RequestBody(required = false) Map<String, Object> fields) {
    log.info("[ENDPOINT] Received request to update user with id {}", id);
    return ResponseEntity.ok(userService.update(id, fields));
  }
}
