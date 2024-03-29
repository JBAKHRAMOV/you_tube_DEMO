package com.company.controller;

import com.company.changeDto.ChangeEmailDTO;
import com.company.changeDto.ChangePasswdDTO;
import com.company.changeDto.NameSurChangeDTO;
import com.company.dto.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping("adm/")
    public ResponseEntity<?> createProfile(@RequestBody @Valid ProfileDTO dto,
                                           HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("create profile: {}", dto);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") Integer id) {
        log.info("get profile by id: {}", id);
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid NameSurChangeDTO dto,
                                           HttpServletRequest request) {
        log.info("update student: {}", dto);
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(profileService.update(pId, dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProfile(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        log.info("delete student: {}", pId);
        return ResponseEntity.ok(profileService.delete(pId));
    }


    @PutMapping("/image")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        log.info("update image: {}", file + "  pId: " + pId);
        try {
            return ResponseEntity.ok(profileService.updateImage(file, pId));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Attach not found");
        }


    }

    @PutMapping("/changePasswd")
    public ResponseEntity<?> changePasswd(@RequestBody @Valid ChangePasswdDTO dto,
                                        HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("change password: {}", dto);
        return ResponseEntity.ok(profileService.changePassword(dto));
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody @Valid ChangeEmailDTO dto,
                                         HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        log.info("change password: {}", dto);
        return ResponseEntity.ok(profileService.changeEmail(dto, pId));
    }

    @PutMapping("/changeEmailVer/{jwt}")
    public ResponseEntity<?> emailVerification(@PathVariable("jwt") String jwt) {
        log.info("change password: {}", jwt);
        return ResponseEntity.ok(profileService.changeEmailVerification(jwt));
    }


}
