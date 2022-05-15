package com.company.controller;

import com.company.dto.TagDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TagService;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto,
                                    HttpServletRequest request) {
        log.info("create: {}", dto );
        try {
            return ResponseEntity.ok(tagService.create(dto));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Already exists: " + e.getMessage());
        }
    }


    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid TagDTO dto,
                                    HttpServletRequest request) {
        log.info("update: {}", "id: "+id+"  "+dto );
        return ResponseEntity.ok(tagService.update(id, dto));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        log.info("dlete: {}", id);
        JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(tagService.delete(id));
    }
}
