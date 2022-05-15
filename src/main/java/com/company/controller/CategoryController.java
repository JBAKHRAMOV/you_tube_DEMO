package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
<<<<<<< HEAD
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto) {
=======
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
>>>>>>> 243834d (Initial commit)
        log.info("create : {}", dto );
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
<<<<<<< HEAD
                                     @RequestParam(value = "size", defaultValue = "3") int size) {
=======
                                     @RequestParam(value = "size", defaultValue = "3") int size,
                                     HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
>>>>>>> 243834d (Initial commit)
        log.info("find all : {}", "page: "+page+" size: "+size );
        return ResponseEntity.ok(categoryService.getList(page, size));
    }

    @GetMapping("/adm/{id}")
<<<<<<< HEAD
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
=======
    public ResponseEntity<?> getById(@PathVariable("id") Integer id,
                                     HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
>>>>>>> 243834d (Initial commit)
        log.info("get by id : {}", id );
        return ResponseEntity.ok(categoryService.getById(id));
    }

<<<<<<< HEAD
    @GetMapping("/public/list/{lang}")
    public ResponseEntity<?> getList(@PathVariable("lang") LangEnum lang) {
        log.info("get list : {}", lang );
        return ResponseEntity.ok(categoryService.getRegionList(lang));
    }

=======
>>>>>>> 243834d (Initial commit)
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody @Valid CategoryDTO dto,
                                    HttpServletRequest request) {
<<<<<<< HEAD
        log.info("update : {}", "id: "+id+" "+dto );
        return ResponseEntity.ok(categoryService.update(id, dto,JwtUtil.getIdFromHeader(request, ProfileRole.USER)));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
=======
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        log.info("update : {}", "id: "+id+" "+dto );
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
>>>>>>> 243834d (Initial commit)
        log.info("delete : {}", id );
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
