package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.RoleService;
import com.search.pharmacy.ws.model.RoleDTO;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDTO));
  }

  @GetMapping
  public ResponseEntity<List<RoleDTO>> getRoles() {
    return ResponseEntity.ok(roleService.getRoles());
  }

  @PatchMapping("{id}")
  public ResponseEntity<RoleDTO> updateRole(
      @PathVariable(value = "id") Long id, @RequestBody Map<String, Object> fields) {
    return ResponseEntity.ok(roleService.updateRole(id, fields));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(@PathVariable(value = "id") Long roleId) {
    roleService.deleteRole(roleId);
    return ResponseEntity.ok().build();
  }
}
