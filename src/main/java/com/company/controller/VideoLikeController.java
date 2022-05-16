package com.company.controller;

import com.company.dto.TagDTO;
import com.company.dto.VideoLikeDto;
import com.company.repository.VideoLIkeService;
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
@RequestMapping("/videolike")
public class VideoLikeController {
    @Autowired
    private VideoLIkeService videoLIkeService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid VideoLikeDto dto,
                                    HttpServletRequest request) {
        log.info("create: {}", dto );
        Integer pId= JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.create(pId,dto));
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> get(@PathVariable("key")String key,
                                    HttpServletRequest request) {
        log.info("create: {}", key );
        Integer pId= JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.read(pId,key));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody @Valid VideoLikeDto dto,
                                    HttpServletRequest request) {
        log.info("create: {}", dto );
        Integer pId= JwtUtil.getIdFromHeader(request);

        return ResponseEntity.ok(videoLIkeService.update(pId,dto));
    }
}
