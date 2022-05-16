package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login", notes = "This method for login")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid AuthDTO dto) {
        log.info("Login: {}", dto);
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Authorization: {}", dto);
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("verification : {}", jwt );
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }

}
