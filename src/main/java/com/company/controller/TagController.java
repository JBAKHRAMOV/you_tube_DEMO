package com.company.controller;

import com.company.dto.TagDTO;
import com.company.enums.ProfileRole;
import com.company.service.TagService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto) {

        log.info("create: {}", dto);

        try {

            return ResponseEntity.ok(tagService.create(dto));

        } catch (DataIntegrityViolationException e) {

            e.printStackTrace();
            return ResponseEntity.badRequest().body("Already exists: " + e.getMessage());
        }
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "3") int size,
                                     HttpServletRequest request) {

        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        log.info("find all : {}", "page: " + page + " size: " + size);

        return ResponseEntity.ok(tagService.getList(page, size));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid TagDTO dto,
                                    HttpServletRequest request) {

        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        log.info("update: {}", "id: " + id + "  " + dto);


        return ResponseEntity.ok(tagService.update(id, dto));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {

        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        log.info("delete: {}", id);

        return ResponseEntity.ok(tagService.delete(id));
    }
}
