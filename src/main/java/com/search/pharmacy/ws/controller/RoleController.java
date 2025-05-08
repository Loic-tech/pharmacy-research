package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.RoleService;
import com.search.pharmacy.ws.model.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
