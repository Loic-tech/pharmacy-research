package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.UserService;
import com.search.pharmacy.ws.model.UserDTO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        log.debug("[ENDPOINT] request to create user");
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        log.debug("[ENDPOINT] request to get all users");
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") Long id) {
        log.debug("[ENDPOINT] request to get user with id {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId) {
        log.info("[ENDPOINT] Received request to delete user with id {}", userId);
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id, @RequestParam(required = false) Map<String, Object> fields) {
        log.info("[ENDPOINT] Received request to update user with id {}", id);
        return ResponseEntity.ok(userService.update(id, fields));
    }
}