package com.charles.knightonline.controller;

import com.charles.knightonline.model.dto.ResponseDTO;
import com.charles.knightonline.model.dto.UserBasicDTO;
import com.charles.knightonline.model.dto.UserDTO;
import com.charles.knightonline.model.dto.UserNameGenderDTO;
import com.charles.knightonline.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to create user: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "update user")
    @PutMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid UserNameGenderDTO dto) {
        log.info("REST request to update user: {}", dto);
        return ResponseEntity.ok(service.update(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user")
    @GetMapping("/{id}")
    public ResponseEntity<UserBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get user: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @Operation(summary = "Get user detail")
    @GetMapping("/detail")
    public ResponseEntity<UserBasicDTO> getDetail() {
        log.info("REST request to get user detail");
        return ResponseEntity.ok(service.getDetail());
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search user name")
    @GetMapping("/search")
    public ResponseEntity<Page<UserBasicDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search users");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }
}
